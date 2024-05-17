import sys
fail_state = (2,4)
succ_state = (3,4)

direction = ['u', 'd', 'r', 'l']

true_direction = [[0,2,3], [1,2,3], [0,1,2],[0,1,3]]

def get_valid_action(state):
	x, y = state
	u, d, r, l = True, True, True, True
	if x + 1 > 3:
		u = False
	if x + 1 == 2 and y == 2:
		u = False
	
	if x - 1 < 1:
		d = False
	if x - 1 == 2 and y == 2:
		d = False
	
	if y + 1 > 4:
		r = False
	if y + 1 == 2 and x == 2:
		r = False

	if y - 1 < 1:
		l = False
	if y - 1 == 2 and x == 2:
		l = False

	return [u, d, r, l]

def get_dest_pos(state, dir):
	valid = get_valid_action(state)[dir]
	if not valid:
		return None
	x, y = state
	delta = [(1, 0), (-1, 0), (0, 1), (0, -1)]
	dx, dy = delta[dir]
	dest = (x + dx, y + dy)
	return dest

def get_best_action(state, values, reward):
	x, y = state
	max_val = float("-inf")
	max_dir = 0
	for i, dir in enumerate(get_valid_action(state)):
		if dir:
			next_true_state = get_dest_pos(state, i)
			val = 0.8 * (reward[next_true_state] + values[next_true_state])
			for d in true_direction[i][1:]:
				next_state = get_dest_pos(state, d)
				if next_state is not None:
					val += 0.1 * (reward[next_state] + values[next_state])
				else:
					val += 0.1 * (reward[next_true_state] + values[next_true_state])
			if val > max_val:
				max_val = val
				max_dir = i
	return max_val, max_dir

def value_iteration(th, reward):
	delta = float("inf")
	all_state = [(x,y) for x in range(1,4) for y in range(1,5)]
	all_state.remove((2,2))
	values = {key:0 for key in all_state}
	rewards = {key:reward for key in all_state}
	rewards[(2,4)] = -1
	values[(2,4)] = -1
	rewards[(3,4)] = 1
	values[(3,4)] = 1
	action = {key:0 for key in all_state}

	iteration = 0
	while delta > th:
		delta = 0
		for state in all_state:
			if state == fail_state or state == succ_state:
				values[state] = rewards[state]
				continue
			max_val, max_dir = get_best_action(state, values, rewards)
			delta = max(delta, abs(max_val - values[state]))
			values[state] = max_val
			action[state] = max_dir
		iteration += 1
	#print("Total iter %d" % iteration)
	return values, action

def draw_values(values):
	for i in range(3, 0, -1):
		for j in range(1, 5):
			try:
				print("%5.2f" %values[(i, j)], end=" ")
			except:
				print(" " * 6,end="")
		print("\n",end="")

def draw_policy(actions):
	for i in range(3, 0, -1):
		for j in range(1, 5):
			try:
				print("%s" %direction[actions[(i, j)]], end=" ")
			except:
				print(" " * 2,end="")
		print("\n",end="")

def print_policy(actions):
	for i in range(1, 4):
		for j in range(1, 5):
			try:
				print("%d, %d, %s"%(i, j, direction[actions[(i, j)]]))
			except:
				continue

if __name__ == "__main__":
	r = float(sys.argv[-1])
	values, actions = value_iteration(1e-10, r)
	print_policy(actions)
	#draw_values(values)
	#draw_policy(actions)
