import matplotlib.pyplot as plt
from math import log
from sklearn.linear_model import LinearRegression
import numpy as np


if __name__ == '__main__':
    X_n= [10000, 20000, 40000, 80000, 160000, 320000, 640000, 1280000, 2560000, 5120000, 10240000] # Number of inserted points
    Y_runtime = [0.011, 0.026, 0.043, 0.072, 0.147, 0.276, 0.694, 1.584, 3.736, 9.445, 30.066]
    index = list(range(11))
    
    X_lg = []
    Y_lg = []
    X_n_sci = []
    for x in X_n: 
        X_lg.append(log(x, 2.0))
        X_n_sci.append(int(x/10000))

    for y in Y_runtime:
        Y_lg.append(log(y, 2.0))

    
    print(index)
    print(X_lg)
    print(Y_lg)

    plt.figure(1)
    plt.bar(index, Y_runtime)
    plt.xlabel('Number of Points inseterted in Kd-tree (1e4)')
    plt.ylabel('Running time of insertions (sec)')
    plt.title('Kd-tree insertion runtime analysis')
    plt.xticks(index, X_n_sci)
    # ax.ticklabel_format(style='sci',axis='both')
    # plt.ticklabel_format(style= 'sci', axis='x')
    plt.savefig('Result_runtime.png')

    plt.figure(2)
    plt.xlabel('lg(N)')
    plt.ylabel('lg(Runtime of insertions)')
    plt.title('growth rate experiment')
    plt.scatter(X_lg, Y_lg, color = 'b', s = 0.3, label='experiment result')
    
    # linear regression fit the runtime curve
    X = np.array(X_lg).reshape(-1, 1)
    Y = np.array(Y_lg).reshape(-1, 1)

    lrModel = LinearRegression()
    lrModel.fit(X, Y)
    Y_pred = lrModel.predict(X)
    coeff = lrModel.coef_
    print('growth rate b (for aN^b): ', coeff)
    print('intercept a for aN^b: ', lrModel.intercept_)

    plt.plot(X, Y_pred, color='r', label='fitting runtime')
    plt.legend()
    plt.savefig('growth_rate_log')

