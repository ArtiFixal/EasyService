USE easyservice;

INSERT INTO services VALUES
(NULL,"diagnostic testing",50),
(NULL,"cleaning",80),
(NULL,"part replacement",100),
(NULL,"PC repair",70),
(NULL,"laptop repair",100),
(NULL,"mobile repair",130),
(NULL,"operating system installation",60),
(NULL,"assemble pc",100);

INSERT INTO manufacturers VALUES
(NULL,"Manufacturer A"),
(NULL,"FinePC"),
(NULL,"GoodPC"),
(NULL,"FastDevice"),
(NULL,"SmartDevice");

INSERT INTO types VALUES
(NULL,"cpu"),
(NULL,"gpu"),
(NULL,"motherboard"),
(NULL,"power supply"),
(NULL,"display"),
(NULL,"keyboard"),
(NULL,"touchpad"),
(NULL,"disk");

INSERT INTO statuses VALUES
(NULL,"placed"),
(NULL,"recived"),
(NULL,"diagnosed"),
(NULL,"fixing"),
(NULL,"waiting for parts"),
(NULL,"ready to pickup"),
(NULL,"finished");