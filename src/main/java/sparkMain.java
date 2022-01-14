import com.sun.codemodel.internal.JForEach;
import org.apache.spark.SparkException;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.*;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import scala.Function1;
import scala.Tuple2;

import java.lang.reflect.Array;
import java.util.*;

import static org.apache.spark.sql.functions.col;

public class sparkMain {
    /**
     *
     * @param args String array
     */
    public static void main(String[] args) throws SparkException {
        Logger.getLogger("org").setLevel(Level.OFF);
        SparkSession spark = null;
        //Logger.getLogger("akka").setLevel(Level.OFF);
        System.setProperty("hadoop.home.dir", "/Users/zaibos/Documents/hadoop-2.7.4/bin");
        //SparkConf conf= new SparkConf().setAppName("Java Spark").setMaster("local[*]");
        try{
            spark = SparkExecutor.sparkMethod("local");
        }catch(Exception exception){
            System.out.println("Spark driver not launched");
            throw new SparkException(exception.getMessage());
        }

        Dataset<Row> ds = spark.read().parquet("/Users/zaibos/Documents/results.parquet").
                filter(col("HomeTeam").equalTo("Arsenal"));
        //Dataset<String> ds2 = ds.map((MapFunction<Row, String>) row -> (String) row.getAs(0), Encoders.STRING());
        JavaRDD<Row> javaRDD=ds.toJavaRDD();
        JavaRDD<List<String>> ds2 = javaRDD.map(new Function<Row, List<String>>() {
            @Override
            public List<String> call(Row row) throws Exception {
                List<String> result = new ArrayList<>();
                //final Object o = row.toString();
                result.add(row.getAs("HomeTeam"));
                result.add(row.getAs("Season"));
                return result;
            }
        });


        org.apache.spark.sql.types.StructType colSchema = ds.schema();
        System.out.println(colSchema);

        //ds2.foreach(e -> System.out.println(Arrays.toString(e.toArray())));

        //Dataset<Row> dataFrame = spark.createDataFrame(ds2.rdd(), RowEncoder.apply(colSchema));





        /*
        JavaRDD<Row> javaRDD=ds.toJavaRDD();

        JavaRDD<List<String>> javaListOfString = javaRDD.map(new Function<Row, List<String>>() {
            @Override
            public List<String> call(Row row) throws Exception {
                List<String> result = new ArrayList<>();
                //final Object o = row.toString();
                result.add(row.toString());
                return result;
            }
        });

        javaListOfString.foreach(e -> System.out.println(Arrays.toString(e.toArray())));


        JavaPairRDD<String, List<String>> javaPairRDD = javaListOfString.mapToPair(new PairFunction<List<String>, String, List<String>>() {
            @Override
            public Tuple2<String, List<String>> call(List<String> strings) throws Exception {
                //List<String> k = Arrays.asList(strings.get(0));
                String k = strings.get(0);
                System.out.println("strings ***"+k);
                String[] k1 = k.split(",");
                String z = k1[2];
                return new Tuple2<>(z,strings);
            }
        });

        JavaPairRDD<String, List<String>> sortedJavaRDD = javaPairRDD.flatMapToPair(new PairFlatMapFunction<Tuple2<String, List<String>>, String, List<String>>() {
            @Override
            public Iterator<Tuple2<String, List<String>>> call(Tuple2<String, List<String>> stringListTuple2) throws Exception {
                List<Tuple2<String, List<String>>> pairs2 = new LinkedList<>();
                List<String> stringListTuple3 = stringListTuple2._2;
                String str = stringListTuple2._1;
                Tuple2<String,List<String>> tuple = new Tuple2<>(str,stringListTuple3);

                return ;
            }
        });

        JavaPairRDD<String, List<String>> sortedJavaRDD = javaPairRDD.sortByKey();


        for (Tuple2<String,List<String>> s :sortedJavaRDD.take(5)) {
            System.out.println(s.toString());
        }

        //javaRDD.map
        //ds.write().csv("Users/zaibos/Documents/result.txt");
        //System.out.println("dataset count");
        //ds.show(1,false);

        */
        
        spark.close();

        }
}
