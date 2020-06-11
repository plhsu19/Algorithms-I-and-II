# A Study of Kd-Tree

Pei-Lun Hsu <br>
2020 May

## I. Memory Usage of Kd-tree

	In this section, briefly discuss memory usage of Kd-tree with n points inserted:
    1. Object Kd-Tree: 16(Over Head) + 4 (int VERTICAL) + 4 (int size) + 8 (Node reference root) = 32 bytes
    2. n Nodes= 16(over head) + 8 (Point2D reference) + 16 (2 Node object reference:lb, rt) + 8 (RectHV reference) = 48 bytes * n
    3. n Point2D = 16 (OV) + 8 (double x) + 8 (double y) = 32 bytes * n
    4. n RectHV = 16 (OV) + 8 * 4 (double xmin, ymin, xmax, ymax) = 48 bytes * n

    Total: 32 (1 object Kd-Tree) + n * 48 (n Nodes) + n * 32 (n Points) + n * 48 (n RectHV) = 
    128n + 32 bytes

    ==> empirical result: 128n + 32 bytes
    
## II. Runtime Analysis of insert()
### Model of Kd-tree's Runtime for insert()

	* Give expected running time in seconds (using tilde notation) to build a 2d-tree on n random points in the unit square:
    For normal BST, insert/search an item in to a BST with n items needs 1.39 lgn compares, i.e, Runtime ~ 1.39 lgn
    To insert n random points in Kd-tree, it will need:
    1. Declaration of new Node: ~ n 
    2. Compare with the Nodes: 
    ~ 1.39 * (1 + lg2 + lg3 + lg4 + ... lg(n-1)) = 1.39 * (1 + lg(n!) - lg n) = T(n)
    1.39c * (1 + 0.5nlgn - lgn) <= T(n) <= 1.39c * (1 + nlgn - lgn)
    T(n) ~ (1.39 to 1.39/2) nlgn
    Model: T(n) ∈ O(nlgn), T(n) ∈ Ω (nlgn) => T(n) ∈ Θ(nlgn) (http://georgeballinger.ca/math126/lognfactorial.pdf)
    Because it is difficult to calculate the Tilde (leading term) of lgn!, I can only give expected astmptotic order of growth(Θ) for runtime T(n).
	

### Methodology for testing Runtime for insert()

	* Insert n nodes from 10000 to 1280000 (step: 2 times) to test the runtime emperically.
    * Obtain the ratio b in T(n) = a * n ^ b according to the results (b expected to be 1 ~ 2 for Θ(nlgn))

### Results

  | Type                          | Runtime  |
  |-------------------------------|----------|
  | 10000                         | 0.011 s  |
  | 20000                         | 0.026 s  |
  | 40000                         | 0.043 s  |
  | 80000                         | 0.072 s  |
  | 160000                        | 0.147 s  |
  | 320000                        | 0.276 s  |
  | 640000                        | 0.694 s  |
  | 1280000                       | 1.584 s  |
  | 2560000                       | 3.736 s  |
  | 5120000                       | 9.445 s  |
  | 10240000                      | 30.066 s |

    T(n) = aN^b (sec): b = 1.105; a = 2 ^ (-21.53) //3.03e-7

### Discussion
    The experimental runtimes of inserting n points basically match the big Theta(nlgn), that is b is slightly lager than 1 (logrithmic linear)

## III. Runtime analysis of nearest()
    How many nearest-neighbor calculations can your 2d-tree implementation perform per second for input100K.txt (100,000 points) and input1M.txt (1 million points). How about Brute Force method?
    
## Model of Kd-tree's Runtime for nearest()
    * Kd-tree: Average case for nearest() T(N) ~ logN (when N points were inserted in tree randomly) (cf: ~ 1.39lgN for BST)
    * BForce: T(N) ~ N
    * Brute force / Kd-tree ~ c * N / lgN (expected)

  | Type                            |Runtime (Expected)     |
  |---------------------------------|-----------------------|
  | 1000 times for N = 100k (Tree)  | T(100k)               |
  | 1000 times for N = 1M  (Tree)   | 1.2 T(100k)           |
  | 1000 times for N = 100k (BF)    | Tb(100k)              |
  | 1000 times for N = 1M   (BF)    | 10 Tb(100k)           |

## Result

  | Type                             | Runtime per search / searches per sec |
  |----------------------------------|---------------------------------------|
  | 1 nearest() for N = 100k (Tree)  |  1.22E-6 s/ ~800,000 times            |
  | 1 nearest() for N = 1M  (Tree)   |  3.17E-6 s/ ~320,000 times            |
  | 1 nearest() for N = 100k (BF)    |  4.57E-3 s/ ~240 times                |
  | 1 nearest() for N = 1M   (BF)    |  9.39E-2 s/ ~11 times                 |

  | Type                             | Runtime per search  |
  |----------------------------------|---------------------|
  | 1000 nearest() for N = 100k (Tree)  |   0.006 s (0.011)|
  | 1000 nearest() for N = 1M  (Tree)   |   0.009 s (0.012)|
  | 1000 nearest() for N = 100k (BF)    |   4.819  s       |
  | 1000 nearest() for N = 1M   (BF)    |   106.32 s       |

## Discussion

	* Number of nearest-neighbor calculations within 1 sec:
    Kd-Tree(100k points): 800k nearest searches (3300X of brute froce)
    Kd-Tree(1M points): 320k nearest searches   (29090X of brute froce)
    Point-Set(100k points): 240 nearest searches (21.81X of 1M-Point-Set)
    Point-Set(1M points): 11 nearest searches
    * Kd-Tree:
        the ration of T(1M)/T(100K) = 1.5 is slightly larger than expected ratio 1.2. However, the variation of these 2 runtimes are large. The reasons are probably because:
        1. The runtime per search changes very little between two input (log10^5 -> log10^6)
        2. The search number is still small (1000), and the distribution of target points to search in different testset can still affect the search path and thus total runtime to certain level. The experimental result does not always match with the model analysis.
    * Brute-Force: 
        The ration of Tb(1M)/Tb(100k) = 22 is much larger than expected ratio of 10, I am not sure why cause this result because the runtime for each nearest point calculation should incrase 10X to loop through all the points in the set which are 10 times. Even if there are some fixed costs that does not change with the number of points, it should less than 10X, not larger. The only reason that I can think of is that there exist some costs that will increase with the number of inserted points when loops through (iterates) each point, e.g., iterates through sorted N points (~ lgN ?/each point).
    * Kd-Tree vs. Brute-Force: 
        ratio of T(100k)/Tb(100k) = 803, T(1M)/Tb(100k) = 11813. (expected: 6024/50175)
        It is hard to expect a ratio between kdtree and brute force with same n items because there are different operation costs for each nearest-neighbor calculation (for each point comparison). However, we can see that as the number of points in the Kd-Tree/Set increase, the ratio becomes larger between Kd-Tree and brute force, which matches the 10^n/lgn relation as n grows. Moreover, the growth of ratio is larger than the growth of ratio of compare number, i.e., (10^6/log10^6)/(10^5/log10^5) = 8.33 -> (empirical) 11813/803 = 14.71, which might because the operation cost for each comparison in brute force increases with n.
        In conclusion, the benefit of Kd-tree will become more obvious when the number of base points n becomes larger.