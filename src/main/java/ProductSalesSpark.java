import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkException;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.expressions.WindowSpec;

import java.net.DatagramSocket;


public class ProductSalesSpark{
    SparkSession spark = null;

    public static void main(String[] args) throws SparkException {
        Logger.getLogger("org").setLevel(Level.OFF);
        SparkSession spark = null;
        System.setProperty("hadoop.home.dir", "/Users/zaibos/Documents/hadoop-2.7.4/bin");
        try {
            spark = SparkExecutor.sparkMethod("local");
        } catch (Exception exception) {
            System.out.println("Spark driver not launched");
            throw new SparkException(exception.getMessage());
        }


         WindowSpec window = Window.partitionBy("product_id").orderBy(org.apache.spark.sql.functions.col("total_quantity_sold").desc());
        WindowSpec window_asc = Window.partitionBy("product_id").orderBy(org.apache.spark.sql.functions.col("total_quantity_sold").asc());

        Dataset<Row> product = spark.read().parquet("/Users/zaibos/Documents/DatasetToCompleteTheSixSparkExercises/products_parquet");

        Dataset<Row> seller = spark.read().parquet("/Users/zaibos/Documents/DatasetToCompleteTheSixSparkExercises/sellers_parquet");

        Dataset<Row> sales = spark.read().parquet("/Users/zaibos/Documents/DatasetToCompleteTheSixSparkExercises/sales_parquet");

        Dataset<Row> poduct_sold_day_wise = sales.groupBy("product_id","date").agg(org.apache.spark.sql.functions.sum("num_pieces_sold").alias("sum"));
        //poduct_sold_day_wise.show();

//        Dataset<Row> read1 = spark.read().parquet("/Users/zaibos/Documents/result/part-00191-07119ac3-e469-4f0d-8501-dab8d6dee16d-c000.snappy.parquet");
//        read1.select("date").show();
//        Dataset<Row> read2 = spark.read().parquet("/Users/zaibos/Documents/result/part-00176-07119ac3-e469-4f0d-8501-dab8d6dee16d-c000.snappy.parquet");
//        read2.select("date").show();
        //Product_id,Sales_id -> sum(products sale)
        Dataset<Row> sum_of_product_sale_by_seller_unique = sales.groupBy("product_id","seller_id").agg(org.apache.spark.sql.functions.sum("num_pieces_sold").alias("total_quantity_sold"));


        //dense_rank desc for 2nd most seller for each product
        Dataset<Row> sales_table = sum_of_product_sale_by_seller_unique.withColumn("rank_desc",org.apache.spark.sql.functions.dense_rank().over(window)).
                withColumn("rank_asc",org.apache.spark.sql.functions.dense_rank().over(window_asc));
        //sales_table.printSchema();

        Dataset<Row> single_seller = sales_table.filter(org.apache.spark.sql.functions.col("rank_desc").equalTo(org.apache.spark.sql.functions.col("rank_asc"))).
                select(org.apache.spark.sql.functions.col("product_id").alias("single_seller_product_id"), org.apache.spark.sql.functions.col("seller_id").alias("single_seller_seller_id"),
                        org.apache.spark.sql.functions.lit("Only seller or multiple sellers with the same results").alias("type"));
        //System.out.println("single seller"+single_seller.count());
        //single_seller.printSchema();

        Dataset<Row> second_seller = sales_table.where(org.apache.spark.sql.functions.col("rank_desc").equalTo(2)).select(
                org.apache.spark.sql.functions.col("product_id").alias("second_seller_product_id"), org.apache.spark.sql.functions.col("seller_id").alias("second_seller_seller_id"),
                org.apache.spark.sql.functions.lit("Second top seller").alias("type"));

        //System.out.println("second seller"+second_seller.count());
        //second_seller.printSchema();




        //Dataset<Row> result= sales_table.filter(org.apache.spark.sql.functions.col("rank_desc").equalTo(2));
        //poduct_sold_day_wise.repartition(poduct_sold_day_wise.col("date")).write().parquet("/Users/zaibos/Documents/result/");

        //Dataset<Row> product_sales_window = sales.withColumn("rank", org.apache.spark.sql.functions.rank().over(window).desc());


    }
}
