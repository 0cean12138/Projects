
import math
import numpy as np
import sys

# read csv data
data = []
with open('hw3_dataset.csv') as f:
    for l in f:
        r = l.split(',')
        r = [x.strip() for x in r]
        data.append(r)

# distinct values for each feature   
kinds = [sorted(set([data[r][i] for r in range(len(data))])) for i in range(len(data[0]))]

# split the data according to feature with column index ind
def splitData(d, ind):
    cat = kinds[ind]
    partition = {k:[] for k in cat}
    for k in cat:
        partition[k] = [d[i] for i in range(len(d)) if d[i][ind] == k]
    
    return partition

# compute entropy
def computeEntropy(d):
    targetPartition = splitData(d, -1)
    n = len(targetPartition['F'])
    p = len(targetPartition['T'])
    
    if n == 0 or p == 0:
        return 0
    
    p1 = n / (n + p)
    p2 = p / (n + p)
    return - (p1 * math.log(p1, 2) + p2 * math.log(p2, 2))


# compute conditional entropy for feature ind
def computeConditionEntropy(data, ind):
    partition = splitData(data, ind)
    total = len(data)
    res = 0
    for k in partition:
        ratio = len(partition[k]) / total
        e = computeEntropy(partition[k])
        res += ratio * e
    return res

# compute information gain
def computeInfoGain(data, ind):
    return computeEntropy(data) - computeConditionEntropy(data, ind)

# node of the tree
class Node:
    def __init__(self):
        # child nodes for non-leaf nodes
        self.child = {}
        # feature column index for non-leaf node
        self.ind = None
        # label for leaf node
        self.label = None
        

# train the decision tree
# return the root node
def train(data, depth):
    targetPartition = splitData(data, -1)
    node = Node()
    
    if depth == 0 or len(targetPartition['T']) == 0 or len(targetPartition['F']) == 0:
        # if depth 0 or the data is pure, it is leaf node

        # set label of leaf node
        if len(targetPartition['T']) > len(targetPartition['F']):
            node.label = 'T'
        else:
            node.label = 'F'
    else:
        # compute information gain for each feature
        infoGain = [computeInfoGain(data, i) for i in range(len(data[0]) - 1)]
        # find the feature with maximum information gain
        maxInd = np.argmax(infoGain)
        node.ind = maxInd
        partition = splitData(data, maxInd)
        for k in partition:
            # recursively train the subtreee for each partition
            node.child[k] = train(partition[k], depth - 1)
        
    return node
    

# predict one row data
def predictOne(root, row):
    if root.label is not None:
        return root.label
    
    return predictOne(root.child[row[root.ind]], row)

# predict all data
def predictAll(root, data):
    
    return [predictOne(root, r) for r in data]


if __name__ == '__main__':
    
    if len(sys.argv) != 2:
        print("usage: python dtree.py <depth>")
        sys.exit(1)

    depth = int(sys.argv[1])

    # train error rates for each round
    train_error_rates = [0] * len(data)

    # test error rates for each round
    test_error_rates = [0] * len(data)

    for i in range(len(data)):
        # train data
        train_data = data[:i] + data[i+1:]

        # train the tree
        root = train(train_data, depth)
        # predict on train data
        train_predict = predictAll(root, train_data)
        train_data_label = np.array(train_data)[:, -1]

        # compute train error rate
        train_err_rate = 1 - np.mean(train_data_label == train_predict)

        # compute test error rate
        test_err_rate = 1 - (1 if predictOne(root, data[i]) == data[i][-1] else 0)
        
        train_error_rates[i] = train_err_rate
        test_error_rates[i] = test_err_rate
    
    # compute average error rate
    train_err = np.mean(train_error_rates)
    test_err = np.mean(test_error_rates)

    print("LOOCV classification error rates for training data:", train_err)
    print("LOOCV classification error rates for test data:    ", test_err)

