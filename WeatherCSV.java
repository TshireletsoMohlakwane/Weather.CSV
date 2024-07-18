
/**
 * WeatherCSV is centered around using multiple methods used for the analysis of CSV files pertaining to
 * past weather data collected over the years. Each method is then tested to validate the accuracy of the processes and 
 * logic used.
 * 
 * 
 * @author (Tshireletso Mohlakwane) 
 * @version (a version number or a date)
 */
import java.io.*;
import edu.duke.*;
import org.apache.commons.csv.*;

public class WeatherCSV {

    public CSVRecord ColdestHourInFile(CSVParser parser) {
        CSVRecord lowestSoFar = null;
        
        for (CSVRecord CurrentRow : parser) {
            if (lowestSoFar == null) {
                lowestSoFar = CurrentRow;
            }//If statement end
            else {
            double currentTemp = Double.parseDouble(CurrentRow.get("TemperatureF"));
            double coldestTemp = Double.parseDouble(lowestSoFar.get("TemperatureF"));
            
            if (currentTemp < coldestTemp) {
              lowestSoFar = CurrentRow;  
            }//if statement end
            }// else statement end
        }//for each loop end
        return lowestSoFar;
    }
    
    public void testColdestInDay() {
     FileResource Tfile = new FileResource();
     CSVRecord lowest = ColdestHourInFile(Tfile.getCSVParser());
     System.out.println("Coldest Temp : " +lowest.get("TemperatureF") + " at " + lowest.get("TimeEST"));
    }
    
    public String fileWithColdestTemperature() {
         
         CSVRecord lowestSoFar= null;
         File coldestFile = null;
         DirectoryResource dr = new DirectoryResource();
         for(File f : dr.selectedFiles()) {
             FileResource Tfile = new FileResource(f);
               CSVParser parser = Tfile.getCSVParser();
             CSVRecord CurrentRow = ColdestHourInFile(Tfile.getCSVParser()) ;
         
            if (lowestSoFar == null) {
                lowestSoFar = CurrentRow;
            }//If statement end
            else {
                double currentTemp = Double.parseDouble(CurrentRow.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(lowestSoFar.get("TemperatureF"));
            
            if (currentTemp < coldestTemp) {
                    lowestSoFar = CurrentRow;
                    coldestFile = f; 
                    
                    }//if statement end
                }// else statement end
            
        }//for each loop end
        return coldestFile.getName();
    } 

    public void testFileWithColdestTemperature() {
    String coldestFile = fileWithColdestTemperature(); 
    System.out.println("File with coldest temperature: " + coldestFile);
    FileResource Tfile = new FileResource();
    CSVRecord lowest = ColdestHourInFile(Tfile.getCSVParser());
    System.out.println("Coldest Temp on that day was : " +lowest.get("TemperatureF"));
    //Reading FILE
    System.out.println("All the Temperatures on the coldest day were: ");
    FileResource fr = new FileResource();
    CSVParser parser = fr.getCSVParser();
    
    for (CSVRecord Record : parser) {
      System.out.print(Record.get("DateUTC") + " ");
      System.out.print(Record.get("TimeEDT") + " ");
      System.out.println(Record.get("TemperatureF"));
    }
    }
    
    public CSVRecord  lowestHumidityInFile (CSVParser parser) {
        CSVRecord lowestHumidity= null;
         for (CSVRecord currentRow : parser) {
             
             if (lowestHumidity == null) {
                 lowestHumidity = currentRow;
             }// if statement end
                 else {
                double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
                double lowHumidity = Double.parseDouble(lowestHumidity.get("Humidity"));
            
            if (currentHumidity < lowHumidity) {
                    lowestHumidity = currentRow;
                    
                    
                    }//if statement end
                }// else
         }// for each loop end
         return lowestHumidity;
    }
    
    public void testlowestHumid() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        //reading file data
         System.out.println("lowest humidity " + csv.get("Humidity") + " at " + csv.get("DateUTC") + " " + csv.get("TimeEST"));
    }
    
    public CSVRecord lowestHumidityInManyFiles() {
         CSVRecord lowestHumidity= null;
         DirectoryResource dr = new DirectoryResource();
         
         for(File f : dr.selectedFiles()) {
             FileResource Tfile = new FileResource(f);
               CSVParser parser = Tfile.getCSVParser();
             CSVRecord CurrentRow = ColdestHourInFile(Tfile.getCSVParser()) ;
         
            if (lowestHumidity == null) {
                lowestHumidity = CurrentRow;
            }//If statement end
            else {
                double currentHumidity = Double.parseDouble(CurrentRow.get("Humidity"));
                double lowestHumidty = Double.parseDouble(lowestHumidity.get("Humidity"));
            
            if (currentHumidity < lowestHumidty) {
                    lowestHumidity = CurrentRow;
                    
                    
                    }//if statement end
                }// else statement end
            
        }//for each loop end
         
        return lowestHumidity;
    }
    
    public void testHumidityManyFiles() {
        CSVRecord lowestHumidity= lowestHumidityInManyFiles();
        System.out.println("lowest humidity was " + lowestHumidity.get("Humidity") + " on the " + lowestHumidity.get("DateUTC") +" " +lowestHumidity.get("TimeEST"));
    }
    
    public double averageTemperatureInFile(CSVParser parser) {
        double avgTemp= 0;
        double Temp = 0;
        double total = 0;
        int icount = 0;
    
        
        for(CSVRecord Record : parser) {
            Temp = Double.parseDouble(Record.get("TemperatureF"));
            total += Temp;
            icount = icount+ 1;

        }
        return avgTemp=total/icount;
    }
    
    public void testAverageTemperatureInFile() {
        FileResource Tfile = new FileResource();
        double Average = averageTemperatureInFile(Tfile.getCSVParser());
        
        System.out.println("Average temperature in file is " + Average);
    }

    public static void main(String[] args) {
        WeatherCSV weather = new WeatherCSV();

        
        System.out.println(weather.fileWithColdestTemperature());
        System.out.println( weather.lowestHumidityInManyFiles());
        weather.testColdestInDay();
        weather.testAverageTemperatureInFile();
        weather.testlowestHumid();
    }
    
    

}
