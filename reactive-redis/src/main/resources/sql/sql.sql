CREATE TABLE `student` (
                       `id` int(11) AUTO_INCREMENT,
                       `code` varchar(50) NOT NULL,
                       `name` varchar(50) NOT NULL,
                       `gender` char(1) NOT NULL,
                       `birthday` date NOT NULL,
                       `address` varchar(300) NULL,
                       `remark` varchar(1000) NULL,
                       `active` tinyint NOT NULL DEFAULT 1,
                       `createdAt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
                       `createdBy` varchar(50) NOT NULL,
                       `updatedAt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE
                           CURRENT_TIMESTAMP(0),
                       `updatedBy` varchar(50) NOT NULL,
                       PRIMARY KEY (`id`),
                       UNIQUE INDEX `idx_main`(`code`)
);



INSERT INTO student ( CODE, NAME, gender, birthday, address, createdBy, updatedBy )
VALUES
    ( 'S0001', 'Tom', 'M', '2001-03-05', NULL, 'TEST', 'TEST' ),
    ( 'S0002', 'Ted', 'M', '2001-06-12', NULL, 'TEST', 'TEST' ),
    ( 'S0003', 'Mary', 'F', '2001--9-12', 'Chicago', 'TEST', 'TEST' );