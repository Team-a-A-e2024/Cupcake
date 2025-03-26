DO $$
DECLARE
    email_address varchar := 'admin@olsker.dk';
    credit_to_add double precision := 50.0;
BEGIN
    UPDATE public.users
    SET credit = credit + credit_to_add
    WHERE email = email_address;
END $$;