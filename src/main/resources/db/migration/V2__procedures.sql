create or replace procedure USP_CREATE_ORDER(
	param_user_id INTEGER,
	out order_id INTEGER
)
LANGUAGE PLPGSQL
AS $$
begin
	IF NOT EXISTS (SELECT 1 FROM users WHERE id=param_user_id) THEN
		ROLLBACK;
		RAISE EXCEPTION 'USER DOES NOT EXISTS';
	END IF;
	INSERT INTO orders(order_timestamp,total_price,status,user_id)
	VALUES(CURRENT_TIMESTAMP,0.0,'PROCESSING',param_user_id) RETURNING id INTO order_id;
END $$;

create or replace procedure usp_create_order_product(
param_order_id integer,
param_product_id integer,
param_quantity integer
)
language plpgsql
as $$
declare
	v_product_id integer;
	available_quantity integer;
	unit_price numeric;
	product_sub_total numeric;

begin
	select p.id,quantity,price into v_product_id,available_quantity,unit_price
	from products p
    join inventory i on p.id=i.product_id
    where p.id=param_product_id;

	if v_product_id is null then
		raise exception 'product does not exists';
		return;
	end if;

	if available_quantity<param_quantity then
		raise exception 'product quantity not available';
		return;
	end if;

	product_sub_total:=param_quantity*unit_price;

	insert into order_product(order_id,product_id,quantity,sub_total)
	values(param_order_id,param_product_id,param_quantity,product_sub_total);

	update orders set total_price=total_price+product_sub_total where id=param_order_id;

	update inventory set quantity=quantity-param_quantity where product_id=param_product_id;

	commit;
end $$;




