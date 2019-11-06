CREATE  PROCEDURE `incentiveProc`(in userId Long)
BEGIN
declare done long default 0; 
declare recid Long default 0;
declare v_canid Long default 0;
declare new_count Long default 1;
declare v_incentive_amount double default 1;
declare v_condition varchar(45) ;
declare v_target int;
declare v_valueType varchar(45) ;
declare v_cnt int;
declare total_amount double default 0;

declare v_am_percentage varchar(45);
declare v_bdm_percentage varchar(45);

declare v_lead_percentage varchar(45);

declare v_am_amount int;
declare v_am_cal_amount double;

declare v_bdm_amount int;
declare v_bdm_cal_amount double;

declare v_lead_amount int;
declare v_lead_cal_amount double;

declare v_primaryContact_id int;
declare v_accountManger_id int;
declare v_secondaryContact_id int;

declare v_lead_id int;
declare flag int default 0;

declare cur1 cursor for 
         SELECT user_id, id FROM testing.candidate where onBoardedStatus='on Boarded'
and onBoardedDate>=(CURDATE()-INTERVAL 1 MONTH) and user_id= userId and incetiveProcessed =0  order by onBoardedDate desc;
declare continue handler for not found set done=1;
SELECT amount,valueType into v_bdm_amount, v_bdm_percentage
FROM testing.incentive_configuration where role='BDM' ;

SELECT amount,valueType into v_am_amount,v_am_percentage
FROM testing.incentive_configuration where role='AM' ;

SELECT amount,valueType into v_lead_amount,v_lead_percentage
FROM testing.incentive_configuration where role='Lead' ;

select reportingId_id  into v_lead_id from user where id= userId;


    set done = 0;
    open cur1;
    igmLoop: loop
        fetch cur1 into recid,v_canid;
        if done = 1 then leave igmLoop;
        end if;
   
SELECT amount,rule,target,valueType, count(*) into v_incentive_amount,v_condition,v_target,v_valueType , v_cnt FROM testing.incentive_configuration where role='recruiter' and target=new_count;
	CASE
WHEN new_count=v_target THEN 
     INSERT INTO `testing`.`incentivenew`
(
`date`,
`recId`,
`canId`,
`cr_Amount`)
VALUES
(
 now(),
recid,
v_canid,
v_incentive_amount);

   
    ELSE
    select new_count;
    SELECT amount,rule,target,valueType into v_incentive_amount,v_condition,v_target,v_valueType  FROM testing.incentive_configuration where role='recruiter' and target =(select max(target) from testing.incentive_configuration where role='recruiter');
  
   INSERT INTO `testing`.`incentivenew`
(
`date`,
`recId`,
`canId`,
`cr_Amount`)
VALUES
(
 now(),
recid,
v_canid,
v_incentive_amount);
 
END case;
 set total_amount = total_amount+v_incentive_amount; 
 
update testing.candidate set incetiveProcessed =1 where id =v_canid;
        SET new_count = new_count + 1;
   set flag =1;   
    end loop igmLoop;
  if(flag=1) then
  SELECT primaryContact_id,accountManger_id,secondaryContact_id,count(*) into v_primaryContact_id,v_accountManger_id, v_secondaryContact_id , v_cnt FROM testing.candidatemapping canmap ,
  client cids, testing.bdmreq bdmreq where candidate_id=v_canid and canmap.bdmReq_id = bdmreq.id and cids.id=bdmreq.client_id; 
    if(v_am_percentage='%') then
    
    
    set v_am_cal_amount = (total_amount*v_am_amount)/100;
    else
   set v_am_cal_amount =v_am_amount;
    end if ;
    
    if(v_bdm_percentage='%') then
      set v_bdm_cal_amount = (total_amount*v_bdm_amount)/100;
  
    else
   set v_bdm_cal_amount =v_bdm_amount;
    end if ;
    
     if(v_lead_percentage='%') then
     select v_lead_amount as leadamount; 
       set v_lead_cal_amount =  (total_amount*v_lead_amount)/100 ;
  
    else
   set v_lead_cal_amount =v_lead_amount;
    end if ;
    
INSERT INTO `testing`.`incentivenew`
(
`date`,
`recId`,
`canId`,
`cr_Amount`)
VALUES
(
 now(),
v_accountManger_id,
v_canid,
v_am_cal_amount);
    
    INSERT INTO `testing`.`incentivenew`
(
`date`,
`recId`,
`canId`,
`cr_Amount`)
VALUES
(
 now(),
v_primaryContact_id,
v_canid,
v_bdm_cal_amount);
    
     INSERT INTO `testing`.`incentivenew`
(
`date`,
`recId`,
`canId`,
`cr_Amount`)
VALUES
(
 now(),
v_lead_id,
v_canid,
v_lead_cal_amount);
   end if; 
    close cur1;
END