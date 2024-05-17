#### DO NOT CHANGE THE BELOW CODE ####

from sklearn.datasets import load_wine
from sklearn.utils import shuffle
from sklearn.model_selection import cross_val_score

from sklearn.linear_model import LogisticRegression
from sklearn.svm import SVC
from sklearn.ensemble import RandomForestClassifier
from sklearn.ensemble import AdaBoostClassifier

import numpy as np

features, labels = load_wine(return_X_y = True)

class_0 = 0
class_1 = 1

features = features[(labels == class_0) | (labels == class_1)]
labels = labels[(labels == class_0) | (labels == class_1)]

num_data, num_features = features.shape

features, labels = shuffle(features, labels, random_state=1)

#### DO NOT CHANGE THE ABOVE CODE ####

#### ADD YOUR CODE HERE ####

