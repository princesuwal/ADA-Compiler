procedure Calc is
   Op: Integer;              
   Disp: Integer;         
   InVal: Integer; 
   a: Integer;    
   bol: boolean;      
begin
   while (true)
   loop
      Put(Disp);
      newline;
      Put(a);
	end loop;
	while (true)
      loop
         Get(Op);
      end loop;

      while(Op <= a)
		loop
			while(Op <= Disp)
			   loop
      	   	      if(Op = a rem 2) then
      	   	         Get(InVal);
      	   	      else 
      	   	      	 Get(a);
      	   	      end if;
      	   	   end loop;
      	end loop;
      newline;
      
      if (Disp <= a) then 
         Disp := a;
      else if (Disp > a) then
            a := a * (2 + 45);
         else
            null;
		 end if;
      end if;
      
end;