import numpy as np
import matplotlib.pyplot as plt
import scipy.linalg as spLin
import RisolviSis

def solve_nsis(A,B):
  # Test dimensione  
    m,n = A.shape
    flag = 0;
    if n!=m:
        print("Matrice non quadrata")
        X = []
        return X
    if np.all(np.diag(A)) != True:
        flag = 1
    
     
    X = np.zeros((n,n))
    PT, L, U = spLin.lu(A)
    P = PT.T.copy()
    if flag == 0:
        for i in range(n):
            y, flag = RisolviSis.Lsolve(L, np.dot(P, B))
            x, flag= RisolviSis.Usolve(U, y)
            X[:,i]=x.reshape(n,)
    else:
        print("Elemento diagonale nullo")
        X=[]
    return X

"""A = np.array([[2,5,8,7], [5,2,2,8], [7,5,6,6], [5,4,4,8]])
b = np.sum(A, axis=1).reshape(4,1)
x = solve_nsis(A, b)
print("Soluzione \n", x)"""

 
#M = D, N = -(E+F)
def jacobi(A,b,x0,toll,it_max):
    
    errore=1000
    d = np.diag(A)
    n = A.shape[0]
    invM = np.diag(1/d)
    E = np.tril(A, -1)
    F = np.triu(A, 1)
    N = - (E + F)
    T=np.dot(invM, N)
    autovalori = np.linalg.eigvals(T)
     
    raggiospettrale = np.max(np.abs(autovalori))
    print("raggio spettrale jacobi", raggiospettrale)
    it=0
     
    er_vet=[]
    while it <= it_max and errore >= toll:
        x = (b + np.dot(N, x0)) / d.reshape((n, 1))
        errore = np.linalg.norm(x - x0) / np.linalg.norm(x)
        er_vet.append(errore)
        x0=x.copy()
        it=it+1
    return x,it,er_vet

"""A = np.array([[4,1,3], [3,4,1], [1,1,17]])
n = A.shape[0]  #do per scontato che sia quadrata altrimeti dovrei controllare che A.shape[0]=A.shape[1]
b = np.sum(A, axis=1).reshape(n,1)
x0 = np.zeros((n,1))
toll = 1e-8
itmax = 100
solJac, it_Jac, err_vet_Jac = jacobi(A,b,x0,toll,itmax)
print("Sol Jac = ", solJac, "\nit Jac", it_Jac)
plt.semilogy(np.arange(it_Jac), err_vet_Jac)"""
#La soluzione deve essere un vettore di 1, il raggio spettrale deve essere < 1


# M = E+D, N = -F
def gauss_seidel(A,b,x0,toll,it_max):
    errore=1000
    d = np.diag(A)
    D = np.diag(d) 
    E = np.tril(A, -1)
    F = np.triu(A, 1)
    M = E + D 
    N = -F
    T = np.dot(np.linalg.inv(M), N)
    autovalori = np.linalg.eigvals(T)
    raggiospettrale = np.max(np.abs(autovalori))
    print("raggio spettrale Gauss-Seidel ", raggiospettrale)
    it = 0
    er_vet = []
    while it <= it_max and errore >= toll:
        temp = b - np.dot(F, x0)
        x, flag = RisolviSis.Lsolve(M, temp)
        errore = np.linalg.norm(x - x0) / np.linalg.norm(x)
        er_vet.append(errore)
        x0=x.copy()
        it=it+1
    return x,it,er_vet

"""A = np.array([[4,1,3], [3,4,1], [1,1,17]])
n = A.shape[0]  #do per scontato che sia quadrata altrimeti dovrei controllare che A.shape[0]=A.shape[1]
b = np.sum(A, axis=1).reshape(n,1)
x0 = np.zeros((n,1))
toll = 1e-8
itmax = 100
solGS, it_GS, err_vet_GS = gauss_seidel(A,b,x0,toll,itmax)
print("Sol Gauss-Seidel = \n", solGS, "\nit Gauss-Seidel", it_GS)
plt.semilogy(np.arange(it_GS), err_vet_GS)"""
#La soluzione deve essere un vettore di 1, il raggio spettrale deve essere < 1


# M = D+E, N = -F
# T = invMomega @ Nomega, Momega = D + omegaE, Nomega = (1 - omega)D - omegaF
def gauss_seidel_sor(A,b,x0,toll,it_max,omega):
    errore = 1000
    d = np.diag(A)
    D = np.diag(d)
    E = np.tril(A, -1)
    F = np.triu(A, 1)
    #Calcolo della matrice di iterazione di Gassu_Seidel SOR
    Momega = D + omega*E
    Nomega = (1-omega)*D - (omega*F)
    T = np.dot(np.linalg.inv(Momega), Nomega)
    autovalori = np.linalg.eigvals(T)
    raggiospettrale = np.max(np.abs(autovalori)) 
    print("raggio spettrale Gauss-Seidel SOR ", raggiospettrale)
    
    M = D + E
    N = -F
    it = 0
    xold = x0.copy()
    xnew = x0.copy()
    er_vet = []
    while it <= it_max and errore >= toll:
        temp = b + np.dot(N, xold)
        xtilde, flag = RisolviSis.Lsolve(M, temp)
        xnew = (1 - omega)*xold + (omega * xtilde)
        errore = np.linalg.norm(xnew - xold) / np.linalg.norm(xnew)
        er_vet.append(errore)
        xold = xnew.copy()
        it = it + 1
    return xnew,it,er_vet

"""A = np.array([[5,0,-1,2], [-2,4,1,0], [0,-1,4,-1], [2,0,0,3]])
toll = 1e-8
it_max = 500
n = A.shape[0]
x0 = np.zeros((n,1))
b = np.sum(A, axis=1).reshape(n,1)
solGS, it_GS, err_vet_GS = gauss_seidel_sor(A,b,x0,toll,it_max,1.0)
print("Sol Gauss-Seidel = \n", solGS, "\nit Gauss-Seidel", it_GS)
plt.semilogy(np.arange(it_GS), err_vet_GS)"""



def steepestdescent(A,b,x0,itmax,tol):
    
#Metodo del gradiente   per la soluzione di un sistema lineare con matrice dei coefficienti simmetrica e definita positiva
    n,m=A.shape
    if n!=m:
        print("Matrice non quadrata")
        return [],[]
    
    
   # inizializzare le variabili necessarie
    x = x0
    r = A.dot(x) - b
    p = -r
    it = 0
    nb = np.linalg.norm(b)
    errore = np.linalg.norm(r) / nb 
    vec_sol=[]
    vec_sol.append(x)
    vet_err=[]
    vet_err.append(errore)
     
# utilizzare il metodo del gradiente per trovare la soluzione
    while it < itmax and errore >= tol: 
        it=it+1
        Ap = A.dot(p)
        rTr= np.dot(r.T, r)
        alpha = rTr / np.dot(p.T, Ap) 
                
        x = x + alpha*p 
        vec_sol.append(x)
        r = r + alpha*Ap
        errore = np.linalg.norm(r) / nb
        vet_err.append(errore)
        p = -r  
        
    
    return x,vet_err,vec_sol,it

"""A = np.array([[8,3], [3,14]])
n = A.shape[0]
b = np.sum(A, axis=1).reshape(n,1)  #somma delle colonne
x0 = np.zeros_like(b)
tol = 1e-8
itmax = 500
xG, vec_errG, vec_solG, itG = steepestdescent(A,b,x0,itmax,tol)
plt.semilogy(np.arange(itG+1), vec_errG)
print("Soluzione gradiente\n", xG)
print("Iterazioni: ", itG)"""



def conjugate_gradient(A,b,x0,itmax,tol):
    
#Metodo del gradiente coniugato per la soluzione di un sistema lineare con matrice dei coefficienti simmetrica e definita positiva
    n,m=A.shape
    if n!=m:
        print("Matrice non quadrata")
        return [],[]
    
    
   # inizializzare le variabili necessarie
    x = x0
    r = A.dot(x) - b 
    p = -r 
    it = 0
    nb = np.linalg.norm(b)
    errore = np.linalg.norm(r) / nb
    vec_sol=[]
    vec_sol.append(x)
    vet_err=[]
    vet_err.append(errore)
# utilizzare il metodo del gradiente coniugato per trovare la soluzione
    while errore >= tol and it < itmax: 
        it = it + 1
        Ap = A.dot(p)
        rTr = np.dot(r.T, r)
        alpha = rTr / np.dot(p.T, Ap) 
        x = x + alpha*p 
        vec_sol.append(x)
        r = r + alpha*Ap 
        gamma = np.dot(r.T, r) / rTr
        errore = np.linalg.norm(r) / nb
        vet_err.append(errore)
        p = -r 
   
    
    return x,vet_err,vec_sol,it

"""A = np.array([[8,3], [3,14]])
n = A.shape[0]
b = np.sum(A, axis=1).reshape(n,1)  #somma delle colonne
x0 = np.zeros_like(b)
tol = 1e-8
itmax = 500
xC, vec_errC, vec_solC, itC = conjugate_gradient(A,b,x0,itmax,tol)
print("Soluzione coniugato\n", xC)
print("Iterazioni: ", itC)"""


#Soluzione di un sistema sovradeterminato facendo uso delle equazioni normali
def eqnorm(A,b):
    G = A.T @ A
    print("Indice di condizionamento di G: ", np.linalg.cond(G))
    
    f = A.T @ b 
    L = spLin.cholesky(G, lower=True)  #lower = True significa che voglio la fattorizzazione di G in
#matrici triangolari inferiori (L@L.T)
    y, flag = RisolviSis.Lsolve(L, f)
    if flag == 0:
        x, flag = RisolviSis.Usolve(L.T, y)
    
    return x

"""x1 = np.array([-3.5, -3, -2, -1.5, -0.5, 0.5, 1.7, 2.5, 3])
y1 = np.array([-3.9, -4.8, -3.3, -2.5, 0.3, 1.8, 4, 6.9, 7.1])
m = np.shape(x1[0])
n = 3    #grado del polinomio di regressione
n1 = n + 1   #gradi di libertà del polinomio
B = np.vander(x1, increasing = True)[:, :n1]
a = eqnorm(B, y1)
xv = np.linspace(np.min(x1), np.max(x1), 200)
pol = np.polyval(np.flip(a), xv)
plt.plot(x1,y1,'ro',xv,pol)
plt.show()
err = spLin.norm(np.polyval(np.flip(a),x1)-y1)**2
print(err)"""



#Soluzione di un sistema sovradeterminato facendo uso della fattorizzazione QR    
def qrLS(A,b):
    n=A.shape[1]  # numero di colonne di A
    #Calcola la fattorizzazione QR di A e utilizzala per calcolare
    #la soluzione nel senso dei minimi quadrati di Ax=b
    Q, R = spLin.qr(A)
    h = Q.T @ b
    x, flag = RisolviSis.Usolve(R[0:n, :], h[0:n])
    r = np.linalg.norm(h[n:])**2
    
    return x, r

"""x1 = np.array([-3.5, -3, -2, -1.5, -0.5, 0.5, 1.7, 2.5, 3])
y1 = np.array([-3.9, -4.8, -3.3, -2.5, 0.3, 1.8, 4, 6.9, 7.1])
m = np.shape(x1[0])
n = 3    #grado del polinomio di regressione
n1 = n + 1   #gradi di libertà del polinomio
B = np.vander(x1, increasing = True)[:, :n1]
a, r = qrLS(B, y1)
xv = np.linspace(np.min(x1), np.max(x1), 200)
pol = np.polyval(np.flip(a), xv)
plt.plot(x1,y1,'ro',xv,pol)
plt.show()
err = spLin.norm(np.polyval(np.flip(a),x1)-y1)**2
print(err)"""



#Soluzione di un sistema sovradeterminato facendo uso della fattorizzazione SVD
def SVDLS(A,b):
     #Calcola la fattorizzazione SVD di A e utilizzala per calcolare
    #la soluzione nel senso dei minimi quadrati di Ax=b
    n = A.shape[1] 
    U, s, VT = spLin.svd(A)
    V = VT.T        
        
    thresh=np.spacing(1)*m*s[0] ##Calcolo del rango della matrice, numero dei valori singolari maggiori di una soglia
    k=np.count_nonzero(s>thresh)
    print("Rango = ", k) 
    d = U.T @ b
    d1 = d[:k].reshape(k, 1) 
    s1 = s[:k].reshape(k, 1)
    #Risolve il sistema diagonale di dimensione kxk avene come matrice    dei coefficienti la matrice Sigma
    c = d1 / s1
    x = V[:,:k] @ c 
    residuo = np.linalg.norm(d[k:])**2
    return x, residuo

"""x= np.array([-3.5,-3, -2, -1.5, -0.5, 0.5, 1.7, 2.5, 3]) 
y = np.array([-3.9,-4.8,-3.3,-2.5, 0.3,1.8,4,6.9,7.1])
#x=np.array( [-3.14,  -2.4,  -1.57,  -0.7,  -0.3,  0,  0.4,  0.7,  1.57]  )
#y=np.array(  [0.02,  -1, -0.9,   -0.72,   -0.2,   -0.04,  0.65,   0.67,   1.1] )
#x  = np.array([1.001, 1.004, 1.005,1.0012, 1.0013,   1.0014,   1.0015,  1.0016])
#y  = np.array([-1.2,-1.0, -0.98,-0.95,-0.9, -1.15, -1.1, -1])
m=x.shape[0]
n=2  #grado del polinomio di regressione
n1=n+1  # gradi di libertà
A=np.vander(x,increasing=True)[:,:n1]
alpha,residuo=SVDLS(A,y)
#alpha=eqnorm(A,y)

print("resdiuo ",residuo)
xv=np.linspace(np.min(x),np.max(x),100)
pol1=np.polyval(np.flip(alpha),xv)
plt.plot(xv,pol1,x,y,'ro')
plt.show()
errore=np.linalg.norm(y-np.polyval(np.flip(alpha),x))**2
print("errore ",errore)"""




#Funzioni per la costruzione del polinomio interpolatore nella base di
#Lagrange
def plagr(xnodi,k):
    """
    Restituisce i coefficienti del k-esimo pol di
    Lagrange associato ai punti del vettore xnodi
    """
    xzeri = np.zeros_like(xnodi)
    n = xnodi.size
    if k == 0:
        xzeri = xnodi[1:n]
    else:
        xzeri = np.append(xnodi[0:k],xnodi[k+1:n])

    num = np.poly(xzeri)
    den = np.polyval(num, x[k])
    
    p = num / den
    
    return p

"""x = np.array([0.0, 1/4, 1/2, 3/4, 1])
n = x.shape[0]
xx = np.linspace(x[0], x[n-1], 200)
M = xx.shape[0]
matL = np.zeros((M, n))

for j in range(n):
    p = plagr(x, j)
    L = np.polyval(p, xx)
    matL[:, j] = L
    #plt.plot(xx, L)
    #plt.plot(x, np.zeros((n,)), 'ro')
    #plt.plot(x[j], 1, 'g*')
    #plt.title("L" + str(j))
    #plt.show()
    #si osserva la proprietà partizione dell'unità, cioè solo una funzione è diversa da 0 (uguale a 1) in ogni punto ed è quella di indice del punto
y = np.random.rand(5)
print(y)
pol = matL@y
plt.plot(xx, pol, x, y, "ro")"""



def InterpL(x, f, xx):
    """"
    %funzione che determina in un insieme di punti il valore del polinomio
    %interpolante ottenuto dalla formula di Lagrange.
    % DATI INPUT
    %  x  vettore con i nodi dell'interpolazione
    %  f  vettore con i valori dei nodi 
    %  xx vettore con i punti in cui si vuole calcolare il polinomio
    % DATI OUTPUT
    %  y vettore contenente i valori assunti dal polinomio interpolante
    %
    """
    n = x.size
    m = xx.size
    L = np.zeros((m,n))
    for k in range(n):
        p = plagr(x, k)
        L[:,k] = np.polyval(p, xx)
    
    return np.dot(L,f)