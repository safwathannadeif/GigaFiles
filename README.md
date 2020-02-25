Function:
    Generates Giga file from collected data. The collected data from different resources and multiple databases are consolidated in single file for further processing (mining) in other remote sites.
    The size of these files can be hundreds of Giga.
Implementation Summary:
  Java 11
  Producer consumer design pattern with a control for memory consumption.
  Read write utilized Java NIO Byte Buffer with configurable Byte Buffer size.
  Collected records can be any type. CVS, json object or any POJO.
  Generic Record Interface for read and write any Record type.
  Different types of records can be mixed & consolidated in the same file.
  For Different types of records, a tag record identifier can be attached for each record to identify the type of the record.
  For the same type of record in the same collected file, the tag can be null to save space
  The max size of the record in Bytes equals  “ Integer.MAX_VALUE - MEGA*10”
  The max for the tag size is 255 Bytes 

Illustration Example: 
   MainTestReadWrite [pkg: com.shd.bigfile.test.maintesting], includes an example that implements the write and read for json employee record.
   In these examples, a generator for employee records-Variable Length Record- is available to generate Giga number of records.
  The elapsed time for writing and reading depends in the IO processor. 
  For example writing 36 Giga file can be done in 21 minutes in one IO processor while the same size (36 Giga) took 4.5 hours in another IO processor.
  Java Memory heap size reached and stabilized to 3.6G with a 3 Byte buffers, and the size of each buffer equals 650M. No Java Memory tuning is required
 Eclipse can Not be used for such Memory size. Any run should be from the command line. The mvn is included to generate the jar to be run from the command line. 
