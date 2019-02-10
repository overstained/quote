This is a cmd-based rate calculation system allowing borrowers to obtain a quote from our pool of lenders for 36 month loans. \
Usage: \
  quote [file_name/path] [borrowed_amount] [additional_arguments] \
  Ex: quote market.csv 1000 \
      quote --help \
      quote market.csv 1000 --delimiter; \
Assumptions made: \
  The program takes a csv file containing columns (Lender, Rate, Available) amongs other possible columns and the order of these
columns dones't matter. \
  If the provided file is too large to be read into memory, the program will probably fail with an outofmemoryexception, depending
on the jvm parameters. \
  If the CSV file is not available at path given(can be relative or absolute), is not accessible or is not a valid CSV file, the program
will end with an exception. \
  How the CSV file is parsed, can also be configured through command arguments: \
    -d or --delimiter in order to specify the delimiter (defaults to comma) \
    --charset - to specify charset(default to UTF-8) \
    -m or --map - to specify corresponding columns in CSV to Rate, Lender, and Available amount ( ex: --mapRATE:MyRateColumn) \
    --help - see a list of commands \
  If the amount to be borrowed cannot be covered by the cummulative available sum from lenders, the program will and with an exception. \
  I have used BigDecimal instead of double for precise computations. \
  You will find some math classes in utils package. I used them for BigDecimal.pow(BigDecimal) since it's lacking in the standard math library. \
 
  
  
