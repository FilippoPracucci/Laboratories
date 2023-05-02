/* 1.	Visualizzare i clienti (customers) in ordine alfabetico*/
SELECT *
FROM customers
GROUP BY ContactName;

/* 2.	Visualizzare i clienti che non hanno il fax*/
SELECT *
FROM customers
WHERE Fax IS NULL;

/* 3.	Selezionare i nomi dei clienti (CompanyName) che iniziano con le lettere P, Q, R, S*/
SELECT *
FROM customers
WHERE CompanyName BETWEEN 'P' AND 'T';

/* 4.	Visualizzare Nome e Cognome degli impiegati assunti (HireDate) dopo il '1993-05-03' e aventi titolo di “Sales Representative”*/
SELECT FirstName, LastName
FROM employees
WHERE HireDate > '1993-05-03' AND Title = 'Sales Representative';

/* 5.	Selezionare la lista dei prodotti non sospesi (attributo discontinued), visualizzando 
anche il nome della categoria di appartenenza*/
SELECT p.*, c.categoryName
FROM products p, categories c
WHERE p.discontinued IS FALSE
AND p.categoryId = c.categoryID;

/* 6. Selezionare gli ordini relativi al cliente ‘Ernst Handel’ (CompanyName)*/
SELECT o.*
FROM orders o JOIN customers c ON (c.customerID = o.customerID)
WHERE c.companyName = 'Ernst Handel';

/* 7.	Selezionare il nome della società e il telefono dei corrieri (shippers) che hanno consegnato 
ordini nella città di ‘Rio de Janeiro’ (ShipCity in orders)
N.B. nella tabella orders l'id corriere è l'attributo ShipVia*/
SELECT DISTINCT s.CompanyName, s.Phone
FROM shippers s JOIN orders o on (s.ShipperID = o.ShipVia)
AND o.ShipCity = 'Rio de Janeiro';

/* 8.	Selezionare gli ordini (OrderID, OrderDate, ShippedDate) per cui la spedizione (ShippedDate)
è avvenuta entro 30 giorni dalla data dell’ordine (OrderDate)*/
SELECT OrderID, OrderDate, ShippedDate
FROM orders
WHERE DATEDIFF(ShippedDate, OrderDate) < 30;

/* 9.	Selezionare l’elenco dei prodotti che hanno un costo compreso tra 18 e 50, ordinando il risultato
in ordine di prezzo crescente */
SELECT *
FROM products
WHERE UnitPrice BETWEEN 18 AND 50
ORDER BY UnitPrice;

/* 10.	Selezionare tutti i clienti (CustomerID, CompanyName) che hanno ordinato il prodotto 'Chang'*/
SELECT c.CustomerID, c.CompanyName
FROM customers c, orders o, `order details` od, products p
WHERE c.CustomerID = o.CustomerID
AND o.OrderID = od.OrderID
AND od.ProductID = p.ProductID
AND p.ProductName = 'Chang';

/* 11.	Selezionare i clienti che non hanno mai ordinato prodotti di categoria ‘Beverages’*/
select c.CustomerID
from customers c
where c.customerID not in (select o.customerID
						   from orders o, `order details` od, products p, categories cat
						   where o.OrderID = od.OrderID
						   and od.ProductID = p.ProductID
						   and p.categoryID = cat.categoryID
						   and cat.CategoryName = 'Beverages');

/* 12.	Selezionare il prodotto più costoso*/
select *
from products
where UnitPrice = (select max(UnitPrice)
				   from products p1);
                   
/* Oppure:
select *
from products
order by UnitPrice desc
limit 1; */

/* 13.	Visualizzare l’importo totale di ciascun ordine fatto dal cliente 'Ernst Handel' (CompanyName)*/
select o.orderID, sum(UnitPrice*Quantity)
from customers c, orders o, `order details` od
where c.customerID = o.customerID
and o.orderID = od.orderID
and c.companyName = 'Ernst Handel'
group by o.orderID;

/* 14.	Selezionare il numero di ordini ricevuti in ciascun anno */
select year(orderDate), count(*)
from orders
group by year(orderDate);


/* 15.	Visualizzare per ogni impiegato (EmployeeID, LastName, FirstName) il numero di clienti distinti serviti per ciascun paese (Country),
visualizzando il risultato in ordine di impiegato e di paese*/
select e.employeeID, LastName, FirstName, c.Country, count(distinct c.customerID) as numCustomers
from employees e, orders o, customers c
where e.employeeID = o.employeeID
and o.customerID = c.customerID
group by e.employeeID, LastName, FirstName, c.Country
order by 2, 3, 4; 

/* 16.	Visualizzare per ogni corriere il numero di consegne effettuate, compresi i dati dei 
corrieri che non hanno effettuato nessuna consegna */
select ShipperID, CompanyName, count(o.OrderID)
from shippers s left join orders o on (s.ShipperID = o.ShipVia)
group by ShipperID, CompanyName;

/* 17.	Visualizzare i fornitori (SupplierID, CompanyName) che forniscono un solo prodotto */
/*select s.SupplierID, s.CompanyName
from suppliers s, orders, o
where s.SupplierID = o.SupplierID
group by s.SupplierID, s.CompanyName
having count(o.productID) = 1;*/

/* 18.	Visualizzare tutti gli impiegati che sono stati assunti dopo Margaret Peacock */

/* 19.	Visualizzare gli ordini relativi al prodotto più costoso */
                                       
/* 20.	Visualizzare i nomi dei clienti per i quali l’ultimo ordine è relativo al 1998  */

/* 21.	Contare il numero di clienti che non hanno effettuato ordini */
                         
/* 22.	Visualizzare il prezzo minimo, massimo e medio dei prodotti per ciascuna categoria */

/* 23.	Selezionare i prodotti che hanno un prezzo superiore al prezzo medio dei prodotti forniti dallo stesso fornitore */
                    
/* 24.	Visualizzare, in ordine decrescente rispetto alla quantità totale venduta, i prodotti che hanno venduto una quantità 
totale superiore al prodotto ‘Chai’ */

/* 25.	Visualizzare il nome dei clienti che hanno fatto almeno due ordini di importo superiore a 15000 */

/* 26.	Individuare i codici dei clienti che hanno fatto un numero di ordini pari a quello del cliente 'Eastern Connection' */

/* 27. Visualizzare il numero di ordini ricevuti nel 1997 e di importo superiore a 10000*/

/* 28. Visualizzare i corrieri che hanno consegnato un numero di ordini superiore al numero di ordini consegnati da Speedy Express. */

/* 29. Visualizzare i clienti che hanno ordinato prodotti di tutte le categorie */

                     

