insert into warehouse (name, address, longitude, latitude) values
                        ('Accme','Madrid',-3.7056721,40.4169019),
                        ('La fabbrica Ternes',NULL,2.2770206,48.8588377)
                        ;

insert into product (warehouse_id, name, description, family, price, active) values
                        (1,'Banana Split','Bananas from Canaria','PERISHABLE',10.9,1),
                        (2,'iPhone6','iPhone 6 Apple Mobile','ELECTRONICS',120.9,1),
                        (1,'Orange','Orange fruit','PERISHABLE',5.3,1),
                        (1,'Melon','Melon fruit','PERISHABLE',3.6,1)
                        ;

insert into stock (warehouse_id, product_id, quantity, expiration_date, lot, serial_number, status) values
                        (1,1,30,'2021-09-30','1234567','','STORED'),
                        (2,2,21,NULL,'','12345678','RESERVED'),
                        (1,4,500,'2021-06-09','1213434','','STORED'),
                        (1,3,10,'2021-05-25','1313134','','STORED')
                        ;
