#### DO NOT CHANGE THE BELOW CODE ####

from sklearn.datasets import load_diabetes
from sklearn.datasets import load_linnerud
from sklearn.model_selection import KFold
from sklearn.utils import shuffle
import numpy as np

features, target_vals = load_diabetes(return_X_y = True)

num_data, num_features = features.shape

# Append a value of 1 to each data point feature vector so we fit the intercept and increment num features
features = np.insert(features, num_features, 1, axis=1)
num_features += 1 

features, target_vals = shuffle(features, target_vals, random_state=1)

#### DO NOT CHANGE THE ABOVE CODE ####
def pack_dataset(X, Y):
    return np.concatenate([X, Y.reshape([-1, 1])], axis=1)

def unpack_dataset(data):
    return (data[:, :-1], data[:, -1].reshape(-1), data.shape[0], data.shape[1]-1)

class LeastSquareLinearRegressor:
    def __init__(self, lmbd) -> None:
        self.lmbda = lmbd
        self.w = None

    def train(self, tr_data):
        X, Y, N, D = unpack_dataset(tr_data)
        Y = Y.reshape([-1, 1])
        self.w = (np.linalg.inv(X.T @ X + self.lmbda * np.eye(D)) @ X.T) @ Y

        #DEBUG
        # print("==> w.shape=" + (str(self.w.shape)))

    def predict(self, X):
        pred = X @ self.w
        return pred

    def eval_RMSE(self, te_data):
        X_te, Y_te, N, D = unpack_dataset(te_data)
        Y = self.predict(X_te).reshape([-1])
        Y_diff = Y_te - Y

        #DEBUG
        # print("==>Y.shape=" + str(Y.shape))
        # print("==>Y_te.shape=" + str(Y_te.shape))
        # print("==>Y_diff:")
        # print(Y_diff)
        # print("==>Y_te")
        # print(Y_te)

        rmse = np.linalg.norm(Y_diff) / np.sqrt(N)
        return rmse

# This holds the average error rate on the test folds for each value of lambda
lambda_val_rmse = []

for lambda_val in [0.1, 1, 10, 100]:

    k_fold = KFold(n_splits=5)

    # This holds the error rates on each of the 5 test folds for a specific value of k
    rmse_vals = []

    for train_idx, test_idx in k_fold.split(features):

        train_features = features[train_idx]
        train_target_vals = target_vals[train_idx]
        test_features = features[test_idx]
        test_target_vals = target_vals[test_idx]

        #### ADD YOUR CODE HERE ####
        tr_data = pack_dataset(train_features, train_target_vals)
        te_data = pack_dataset(test_features, test_target_vals)
        mdl = LeastSquareLinearRegressor(lambda_val)
        mdl.train(tr_data)
        rmse_val = mdl.eval_RMSE(te_data)
        rmse_vals.append(rmse_val)

        #DEBUG
        # print("==>My rmse:")
        # print(rmse_val)
        # print("==>Sklearn rmse:")
        # from sklearn.linear_model import LinearRegression
        # lin = LinearRegression().fit(train_features, train_target_vals)
        # Y_preds = lin.predict(test_features)
        # print("==>test_target_vals.shape=" + str(test_target_vals.shape))
        # print("==>Y_preds.shape=" + str(Y_preds.shape))
        # rmse_lin = np.linalg.norm(Y_preds - test_target_vals) / np.sqrt(test_features.shape[0])
        # print(rmse_lin)

    lambda_val_rmse.append(np.average(rmse_vals))

print('Average test RMSE for each value of lambda:', lambda_val_rmse)
