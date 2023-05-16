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
select s.SupplierID, s.CompanyName, count(*)
from suppliers s, products p
where s.SupplierID = p.SupplierID
group by SupplierID, CompanyName
having count(*) = 1;

/* 18.	Visualizzare tutti gli impiegati che sono stati assunti dopo Margaret Peacock */
select e.*
from employees e
where e.HireDate > (select e1.HireDate
				    from employees e1	
                    where e1.FirstName = 'Margaret' and e1.LastName = 'Peacock');

/* 19.	Visualizzare gli ordini relativi al prodotto più costoso */
select o.*
from orders o, `order details` od
where o.OrderID = od.OrderID
and od.ProductID in (select productID
					 from products
                     where UnitPrice = (select max(UnitPrice)
									   from products p1));

/* 20.	Visualizzare i nomi dei clienti per i quali l’ultimo ordine è relativo al 1998  */
select c.CustomerID, CompanyName
from customers c, orders o
where c.customerID = o.customerID
group by c.customerID, companyName
having max(year(OrderDate)) = 1998;

/* 21.	Contare il numero di clienti che non hanno effettuato ordini */
select count(*)
from customers c
where c.customerID NOT IN (select o.customerID
						   from orders o);
                         
/* 22.	Visualizzare il prezzo minimo, massimo e medio dei prodotti per ciascuna categoria */
select c.categoryID, c.categoryName, min(UnitPrice), avg(UnitPrice)
from products p, categories c
where p.categoryID = c.categoryID
group by c.categoryID, c.categoryName;

/* 23.	Selezionare i prodotti che hanno un prezzo superiore al prezzo medio dei prodotti forniti dallo stesso fornitore */
select p.productID, p.productName, p.UnitPrice
from products p
where p.UnitPrice > (select avg(p1.UnitPrice)
					 from products p1
                     where p1.supplierID = p.supplierID);
                    
/* 24.	Visualizzare, in ordine decrescente rispetto alla quantità totale venduta, i prodotti che hanno venduto una quantità 
totale superiore al prodotto ‘Chai’ */
select od.productID, sum(Quantity)
from `order details` od
group by od.productID
having sum(Quantity) > (select sum(od1.Quantity)
						from `order details` od1, products p
                        where od1.productID = p.productID
                        and p.productName = 'Chai')
order by sum(Quantity) desc;

/* 25.	Visualizzare il nome dei clienti che hanno fatto almeno due ordini di importo superiore a 15000 */
select c.companyName
from customers c, orders o
where c.customerID = o.customerID
and orderID IN (select od.orderID
			    from `order details` od
                group by od.orderID
                having sum(od.UnitPrice * od.Quantity) > 15000);

/* 26.	Individuare i codici dei clienti che hanno fatto un numero di ordini pari a quello del cliente 'Eastern Connection' */
select o.customerID
from orders o
group by o.customerID
having count(*) = (select count(*)
				   from orders o1, customers c
                   where o1.customerID = c.customerID
                   and c.companyName = 'Eastern Connection');

/* 27. Visualizzare il numero di ordini ricevuti nel 1997 e di importo superiore a 10000*/
select count(*)
from orders
where orderID in (select od.orderID
				  from orders o1, `order details` od
                  where o1.orderID = od.orderID
                  and year(o1.orderDate) = 1997
                  group by od.orderID
                  having sum(od.Quantity * od.UnitPrice) > 10000);

/* 28. Visualizzare i corrieri che hanno consegnato un numero di ordini superiore al numero di ordini consegnati da Speedy Express. */


/* 29. Visualizzare i clienti che hanno ordinato prodotti di tutte le categorie */
select c.*
from customers c, orders o, `order details` od, products p
where c.customerID = o.customerID
and o.orderID = od.orderID
and od.productID = p.productID
group by c.customerID
having count(distinct p.categoryID) = (select count(*)
									   from categories c1);	
                     

