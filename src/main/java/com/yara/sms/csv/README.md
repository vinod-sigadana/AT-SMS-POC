# Excel File Download/Upload

## Download
1. Download excel file in protection mode with fields Mobile Number, First Name and Last Name.
2. Admin should not be able edit Mobile Number column since it is read-only.
3. Admin should be able to edit other columns (first name and last name) which are read and write.
4. Except the first name and last name columns, all other columns will be read-only.
5. Admin cannot add a NEW farmer details.
6. Admin can filter all the columns.
7. Sorting is not allowed when sheet is in protection mode.
   
**Note**: In protection mode, specified cells will be read-only and position cannot be altered.

## Upload
1. Excel sheet name should match with “Farmers” (sheet name can be changed while download)
2. Admin can upload the file using interface and can see the farmers details as a response.
3. All farmers' info will be parsed and displayed it to the admin.

## Performance
Below table shows time taken to perform both operations.
1. Prepare dummy data of farmers.
2. Prepare excel sheet.

|Number of records| Time taken|
|-----------------|----------|
|10,000|4 sec|
|50,000|16 sec|
|100,000|30 sec|
|150,000|43 sec|
|230,000|1 min 8 sec|
|450,000|2 min 19 sec|

