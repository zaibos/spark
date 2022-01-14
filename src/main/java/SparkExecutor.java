import org.apache.spark.SparkException;
import org.apache.spark.sql.SparkSession;

public class SparkExecutor {
    private SparkSession spark;
     public static SparkSession sparkMethod(String Mode) throws SparkException {

         try{
             SparkSession spark = SparkSession
                     .builder()
                     .master(Mode)
                     .appName("Spark-Engine")
                     .getOrCreate();
             return spark;

         }catch (Exception e){
             System.out.println("SparkSession not launched -> ");
             throw new SparkException(e.getMessage());
         }
     }
}
