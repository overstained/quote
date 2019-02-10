<h3>This is a cmd-based rate calculation system allowing borrowers to obtain a quote from our pool of lenders for 36 month loans.</h3>
<h3>Usage:</h3>
    <p>quote [file_name/path] [borrowed_amount] [additional_arguments]</p>
    <p>Ex:   quote market.csv 1000<br>quote --help<br>quote market.csv 1000 --delimiter;</p>
<h3>Assumptions made</h3> 
  <p>The program takes a csv file containing required columns (Lender, Rate, Available) amongst other possible columns.The order of the columns doesn't matter.</p>
  <p>If the provided file is too large to be read into memory, the program will probably fail with an outofmemoryexception, depending
on the jvm parameters.</p>
  <p>If the CSV file is not available at path given(can be relative or absolute), is not accessible or is not a valid CSV file, the program
will end with an exception.</p>
  <p>How the CSV file is parsed, can also be configured through command arguments:</p>
  <ul>
    <li>-d or --delimiter in order to specify the delimiter (defaults to comma)</li>
    <li>--charset - to specify charset(default to UTF-8)</li>
    <li>-m or --map - to specify corresponding columns in CSV to Rate, Lender, and Available amount ( ex: --mapRATE:MyRateColumn)</li>
    <li>--help - see a list of commands</li>
  </ul>
  <p>If the amount to be borrowed cannot be covered by the cummulative available sum from lenders, the program will and with an exception. </p>
  <p>I have used BigDecimal instead of double for precise computations.</p>
  <p>You will find some math classes in utils package. I used them for BigDecimal.pow(BigDecimal) since it's lacking in the standard math library.</p>
  <p>In order to test, you can access the folder quote-test - it contains a batch file, the market.csv and the jar package.</p>
 
  
  
