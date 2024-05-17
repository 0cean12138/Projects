#### DO NOT CHANGE THE BELOW CODE ####

from sklearn.datasets import load_wine
from sklearn.utils import shuffle
import numpy as np
from sympy import NumberSymbol

percent_train = 0.8

num_reps = 10

features, labels = load_wine(return_X_y = True)

class_0 = 0
class_1 = 1

features = features[(labels == class_0) | (labels == class_1)]
labels = labels[(labels == class_0) | (labels == class_1)]

num_data, num_features = features.shape
split = int(np.ceil(num_data*percent_train))

features, labels = shuffle(features, labels, random_state=1)

train_features, train_labels = (features[:split], labels[:split])
test_features, test_labels = (features[split:], labels[split:])

num_train_data = train_features.shape[0]
num_test_data = test_features.shape[0]

#### DO NOT CHANGE THE ABOVE CODE ####
def pack_dataset(X, Y):
    return np.concatenate([X, Y.reshape([-1, 1])], axis=1)

def unpack_dataset(data):
    return (data[:, :-1], data[:, -1].reshape(-1), data.shape[0], data.shape[1]-1)

def get_error_rate(Y_pred, Y):
    # DEBUG
    # print("==>Y_pred:\n" + str(Y_pred))
    # print("==>Y:\n" + str(Y.astype(np.int32)))

    return np.sum(np.not_equal(Y_pred, Y)) / (Y_pred.shape[0])

def eval_model(mdl, te_data):
    X_te, Y_te, N, D = unpack_dataset(te_data)
    Y_pred = mdl.predict(X_te)

    #DEBUG
    # print("==>Y_pred.shape" + str(Y_pred.shape))
    # print("==>X_te.shape" + str(X_te.shape))
    # print("==>Y_te.shape" + str(Y_te.shape))

    err_rate = get_error_rate(Y_pred, Y_te)
    return err_rate


class PerceptronCls:
    def __init__(self) -> None:
        self.w = None

    def get_error_term(self, w, x, y):
        err = 0
        # Y_tr_mat = Y_tr.reshape([-1, 1])

        #DEBUG
        # print("==> w.shape:" + str(w.shape))
        # print("==> X_tr.shape:" + str(X_tr.shape))
        # print("==> Y_tr.shape:" + str(Y_tr.shape))
        # print("==> Y_tr_mat.shape:" + str(Y_tr_mat.shape))

        # err = np.sum((Y_tr_mat - np.greater_equal(X_tr @ w,
        #  0).astype(np.int32)) * X_tr, axis=0).reshape([-1, 1])

        err = (y - np.greater_equal(x @ w, 0).astype(np.int32))[0] * x
        err = err.reshape([-1, 1])

        # DEBUG
        # print("==> err.shape=" + str(err.shape))
        # print("==> err.shape=" + str(err))

        return err

    def train(self, tr_data):
        X_tr, Y_tr, N, D = unpack_dataset(tr_data)

        NUM_ITERATION = 100000
        # NUM_ITERATION = 1000
        # init weights
        w = np.random.random([D, 1])
        # start training
        for t in range(1, NUM_ITERATION+1):
            alpha = 1 / np.sqrt(t)
            i = np.random.randint(N)
            dw = alpha * self.get_error_term(w, X_tr[i], Y_tr[i])
            w = w + dw

            #DEBUG
            # print("==> dw:" + str(dw))
            # print("==> w:" + str(w))

        # persist to class member variables
        self.w = w

    def predict_scores(self, X):
        #DEBUG
        # print("==> X.shape=" + str(X.shape))
        # print("==> self.w.shape=" + str(self.w.shape))
        # print("==> (X @ self.w).shape=" + str((X @ self.w).shape))

        return X @ self.w

    def predict(self, X):
        #DEBUG
        # print("==> self.predict_scores(X).shape:" + str(self.predict_scores(X).shape))
        # print("==> greater_equal.shape:" + str(np.greater_equal(self.predict_scores(X), 0).shape))

        return np.greater_equal(self.predict_scores(X), 0).astype(np.int32).reshape([-1])

#DEBUG
# print(test_features.shape)
# print(test_labels.shape)

# Use this to store your error rates for each repetition

error_rates = []
tr_data = pack_dataset(train_features, train_labels)
te_data = pack_dataset(test_features, test_labels)

for repetition in range(num_reps):

    #### ADD YOUR CODE HERE ####
    mdl = PerceptronCls()
    mdl.train(tr_data)
    err_rate = eval_model(mdl, te_data)
    error_rates.append((err_rate))
    print("==> repetition %d/%d, err_rate:%0.3f" % (repetition+1, num_reps, err_rate))

for i in range(num_reps):
    print('%0.3f ' % (error_rates[i]))

print('Average error rate:', np.average(error_rates))
print('Error rate standard deviation:', np.std(error_rates))
