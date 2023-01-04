import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;

;

public class MyController {


    String data1, data2;
    DbConnection obj = new DbConnection();
    Connection conn = obj.connMethod();
    @FXML
    private TextField txt1;
    @FXML
    private TextField txt2;
    @FXML
    private GridPane gp;
    private ObservableList<ObservableList> data;
    @FXML
    private TableView tbl;
    TableColumn col;
    Button btn2;
    private String SQL;

    //CONNECTION DATABASE

    public void buildData() {
        DbConnection obj1;
        Connection c;
        ResultSet rs;
        data = FXCollections.observableArrayList();
        try {

            tbl.setStyle("-fx-background-color:red; -fx-font-color:yellow ");
            obj1 = new DbConnection();
            c = obj1.connMethod();

            //int id = Integer.parseInt(data1);
            //SQL FOR SELECTING ALL OF Table
            SQL = "SELECT * from DEPT";
            //ResultSet,Statement

            rs = c.createStatement().executeQuery(SQL);

            //ResultSet,PreparedStatement
       /*     String SQL1 = "Insert into Profile values(?,?)";
           // String SQL = "SELECT * FROM emp WHERE empno=?";
            PreparedStatement p = conn.prepareStatement(SQL1);
            p.setString(1,data1);
            p.setString(2,data2);
           Boolean status= p.execute();
*/

            //rs = p.executeQuery();

            //ResultSet,CallableStatement
            /*CallableStatement cstmt = c.prepareCall("{call SELECTOR14(?,?)}");
            cstmt.setInt(1, id);
            cstmt.registerOutParameter(2,Types.TIMESTAMP);
            rs=cstmt.executeQuery();
            System.out.println(cstmt.getTimestamp(2));*/
            //rs = cstmt.executeQuery();
//           CallableStatement cstmt = c.prepareCall("{call INSERTDATA(?,?)}");
//            cstmt.setString(1,data1);
//            cstmt.setString(2,data2);
//            cstmt.execute();

            //ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //for (int i = 1; i < rsmd.getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                //TableColumn col = new TableColumn(rsmd.getColumnLabel(i));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>,
                        ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                tbl.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");

            }


            while (rs.next()) {

                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row[1]added " + row);
                data.add(row);

            }


            tbl.setItems(data);
            Document document=new Document();
            PdfWriter.getInstance(document,new FileOutputStream("C:\\Users\\adage\\Documents\\generate.pdf"));
            document.open();
            document.add(new Paragraph((Chunk) data));
            System.out.println((Chunk)data);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error ");
        }
    }
}

