INSERT INTO users(id,first_name,last_name,email,password,role,enabled,locked) VALUES('1','bob','martin','bob@gmail.com','$2a$10$IgwST6ngx.9ELWXAAwoYduRLJ/RAkdo.cyw9gKRio4CyqtGwqwHdq','ADMIN',TRUE,FALSE);
INSERT INTO users(id,first_name,last_name,email,password,role,enabled,locked) VALUES('2','alice','sam','alice@gmail.com','$2a$10$Ufkvxrk9uwu4Y.fdATiMXO9Pq9H8ky1rsoxk5FerE2XOWFzUuxYvW','USER',TRUE,FALSE);
INSERT INTO categories(id,name,status) VALUES(1,'shirts',TRUE);
INSERT INTO categories(id,name,status) VALUES(2,'trousers',TRUE);
INSERT INTO categories(id,name,status) VALUES(3,'tshirts',TRUE);
INSERT INTO items(id,name,description,price,category_id,status) VALUES(1,'nikeL','nike L cotton','2000',1,TRUE);
INSERT INTO items(id,name,description,price,category_id,status) VALUES(2,'nikeXS','nike XS cotton','2000',1,TRUE);
INSERT INTO inventory_item(id,quantity,item_id,created_on) VALUES(1,'20',1,CURRENT_TIMESTAMP);
INSERT INTO inventory_item(id,quantity,item_id,created_on) VALUES(2,'25',2,CURRENT_TIMESTAMP);