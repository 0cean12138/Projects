#### DO NOT CHANGE THE BELOW CODE ####

from sklearn.datasets import load_wine
from sklearn.model_selection import KFold
from sklearn.utils import shuffle
import numpy as np

features, labels = load_wine(return_X_y = True)

class_0 = 0
class_1 = 1

features = features[(labels == class_0) | (labels == class_1)]
labels = labels[(labels == class_0) | (labels == class_1)]

num_data, num_features = features.shape

features, labels = shuffle(features, labels, random_state=1)

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

# compute pairwise operations of N D-dimension vectors using numpy's broadcasting mechanism
def get_dist_mat(X, X_ref, dist_fn):
    return dist_fn(X[:, None, :] - X_ref[None, :, :], axis=-1)

class KNNCls:
    def __init__(self, K) -> None:
        self.K = K
        self.X_nb = None
        self.Y_nb = None

    def train(self, tr_data):
        X_tr, Y_tr, N, D = unpack_dataset(tr_data)

        # just save
        self.X_nb = X_tr
        self.Y_nb = Y_tr.reshape([-1])
    
    def predict(self, X):
        N, D = X.shape
        dist_mat = get_dist_mat(X, self.X_nb, np.linalg.norm)
        leastK_ind_mat = dist_mat.argpartition(self.K-1, axis=1)[:, :self.K]
        f_map_y = lambda i : self.Y_nb[i]
        leastK_y_mat = f_map_y(leastK_ind_mat)
        leastK_y_mat = leastK_y_mat.astype(np.int32)

        #DEBUG
        # print("dist_mat.shape=" + str(dist_mat.shape))
        # print("leastK_ind_mat.shape=" + str(leastK_ind_mat.shape))
        # print("leastK_y_mat.shape=" + str(leastK_y_mat.shape))

        # count votes for each row
        votes = np.zeros([N, 2]).astype(np.int32)
        for i in range(N):
            votes[i, :] = np.bincount(leastK_y_mat[i])

        # get predictions
        preds = np.argmax(votes, axis=1)
        
        #DEBUG
        # print("dist_mat.shape=" + str(dist_mat.shape))

        return preds
    
    def eval_err_rate(self, te_data):
        X_te, Y_te, N, D = unpack_dataset(te_data)
        Y_pred = self.predict(X_te)

        #DEBUG
        # print("==>Y_pred.shape" + str(Y_pred.shape))
        # print("==>X_te.shape" + str(X_te.shape))
        # print("==>Y_te.shape" + str(Y_te.shape))

        err_rate = get_error_rate(Y_pred, Y_te)
        return err_rate


# This holds the average error rate on the test folds for each value of k
k_error_rates = []

# for k in [1, 3, 23, 51, 101]:
for k in [23, 51, 101]:

    k_fold = KFold(n_splits=5)

     # This holds the error rates on each of the 5 test folds for a specific value of k
    error_rates = []

    for train_idx, test_idx in k_fold.split(features):

        train_features = features[train_idx]
        train_labels = labels[train_idx]
        test_features = features[test_idx]
        test_labels = labels[test_idx]

        #### ADD YOUR CODE HERE ####

        #DEBUG
        # print(train_features.shape)
        # print(train_labels.shape)
        # print(test_features.shape)
        # print(test_labels.shape)

        tr_data = pack_dataset(train_features, train_labels)
        te_data = pack_dataset(test_features, test_labels)
        knn_cls = KNNCls(k)
        knn_cls.train(tr_data)
        err_rate = knn_cls.eval_err_rate(te_data)
        error_rates.append(err_rate)

    k_error_rates.append(np.average(error_rates))

print('Average test error for each value of k:', k_error_rates)
