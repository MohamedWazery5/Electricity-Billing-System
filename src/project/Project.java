/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Wazery
 */
public class Project {
    public static void main(String[] args) throws SQLException {  
    }
}
class DB {
    Connection conn;
    Statement st;
    ResultSet rs;

    public DB() throws SQLException {
        try {
            String ConnectionUrl = "jdbc:sqlserver://localhost\\MSI\\MSSQLSERVER01:1433;databaseName=Elec_Bills_System" + ";username=sa" + ";password=12345";
            conn = DriverManager.getConnection(ConnectionUrl);
            st = conn.createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void CloseConnection() throws SQLException
    {
        st.close();
        conn.close(); 
    }
}
 class customer {

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
    private int id;
    private String name;
    private String area;
    private int  role;
    private String username;
    private String pass;
    DB db;
    public customer() throws SQLException
    {   
        this.db=new DB();
    }
    public customer(int UserID,String userName,String Pass,int roleID) throws SQLException{
       this.id=UserID;
       this.username=userName;
       this.pass=Pass;
       this.role=roleID;
       this.db=new DB();
          }
    void setName(String name)
    {
        this.name=name;
    }
    void setArea(String area)
    {
        this.area=area;
    }
    void setRole(int role)
    {
        this.role=role;
    }
    String getName()
    {
        return name;
    }
    String getArea()
    {
        return area;
    }
    int getRole()
    {
        return role;
    }
   public int user_id() throws SQLException{
       String user=LogIn.txtuser.getText();
       String pass=LogIn.txtpass.getText();
        String sgl="select user_id from user_info where user_name='"+user+"' AND password='"+pass+"'";
        db.rs=db.st.executeQuery(sgl);
        db.rs.next();
        int id=db.rs.getInt(1);
        return id;
    }
   public int user_id(String user,String pass) throws SQLException{
        String sgl="select user_id from user_info where user_name='"+user+"' AND password='"+pass+"'";
        db.rs=db.st.executeQuery(sgl);
        db.rs.next();
        int id=db.rs.getInt(1);
        return id;
    }
   public int customer_id() throws SQLException{
        String sgl="select c_id from customer where u_id="+user_id()+"";
        db.rs=db.st.executeQuery(sgl);
        db.rs.next();
        int id=db.rs.getInt(1);
        return id;
    }
   public int meter_state() throws SQLException{
        String sgl="Select m_state from meter_requests where customer_id="+customer_id()+"";
        db.rs=db.st.executeQuery(sgl);
        db.rs.next();
        int state=db.rs.getInt(1);
        return state;
    }
   public int customer_id(int userID) throws SQLException{
        String sgl="select c_id from customer where u_id="+userID+"";
        db.rs=db.st.executeQuery(sgl);
        db.rs.next();
        int id=db.rs.getInt(1);
        return id;
    }
   public String meter_id() throws SQLException{
       String sgl="select m_id from meter_requests where customer_id="+customer_id()+"";
       db.rs=db.st.executeQuery(sgl);
       db.rs.next();
       String id=db.rs.getString(1);
       return id;
   }
   public int reading_id() throws SQLException{
       String sql="select r_id from reading LEFT join bills ON r_id=reading_id  where customer_id="+customer_id()+" AND paid=0  ";
       db.rs=db.st.executeQuery(sql);
       db.rs.next();
       int id=db.rs.getInt(1);
       return id;
   }
    public int reading_idAdd() throws SQLException{
       String sql="select r_id from reading  where customer_id="+customer_id()+" ORDER BY r_id DESC";
       db.rs=db.st.executeQuery(sql);
       db.rs.next();
       int id=db.rs.getInt(1);
       return id;
   }
   public String units() throws SQLException{
       String sql="Select r_id,units from reading LEFT join bills ON r_id=reading_id where customer_id="+customer_id()+" AND paid=0 ";
       db.rs= db.st.executeQuery(sql);
       db.rs.next();
       String u=db.rs.getString(2);
       return u;
   }
   public String getAVG(String area) throws SQLException{
       String sql="Select AVG(units) from reading Inner join customer ON c_id=customer_id where c_area='"+area+"'";
       db.rs= db.st.executeQuery(sql);
       db.rs.next();
       String u=db.rs.getString(1);
       return u;
   }
   public float triff() throws SQLException{
       String sql="select triif_id from customer_triff where customer_id="+customer_id()+"";
       db.rs=db.st.executeQuery(sql);
       db.rs.next();
       String sql2="Select kilo_cost from triff where t_id="+db.rs.getInt(1)+"";
       db.rs=db.st.executeQuery(sql2);
       db.rs.next();
       float f=db.rs.getFloat(1);
       return f;
   }
   void addCustomer(String name,String user_name,String password,String phone,String area,String path,int i) throws SQLException
        {
            String query = "insert into user_info values('"+user_name+"','"+password+"','"+i+"')";
              db.st.executeUpdate(query);
              String que="Select user_id from user_info where user_name='"+user_name+"' AND password='"+password+"'";
              db.rs=db.st.executeQuery(que);
              db.rs.next();
             String query2 = "insert into customer values('"+name+"','"+phone+"','"+area+"','"+path+"','"+db.rs.getInt(1)+"')";
             db.st.executeUpdate(query2);
        }
   void addUser(String user,String pass,int x) throws SQLException{
       String sql="insert into user_info values('"+user+"','"+pass+"',"+x+")";
       db.st.executeUpdate(sql);
   }
   void addMeter(int id) throws SQLException{
       String sql="insert into meter_requests values("+customer_id(id)+",2)";
       db.st.executeUpdate(sql);
   }
    void addTriff(int id) throws SQLException{
       String sql="insert into customer_triff values("+customer_id(id)+",1)";
       db.st.executeUpdate(sql);
   }
   void addReading(int reading , String date) throws SQLException{
       String sql="insert into reading values ('"+customer_id()+"','"+reading+"','"+date+"')";
       db.st.executeUpdate(sql);  
   }
   void addCost(float cost) throws SQLException{
       String sql="insert into bills values ('"+reading_idAdd()+"','"+cost*triff()+"',0,0)";
       db.st.executeUpdate(sql);
   }
   void addComplain(String comp) throws SQLException{
       String sql="insert into complains values ("+customer_id()+",'"+comp+"','null')";
       db.st.executeUpdate(sql);
   }
   void deleteUser(int x) throws SQLException{
       String sql = "delete from user_info where user_id = "+x+"";
       db.st.executeUpdate(sql);
   }
   void updateState() throws SQLException{
       String sql="update bills set paid=1 where reading_id="+reading_id()+"";
       db.st.executeUpdate(sql);
       
   }
   void updateCustomer(int id,String name,String user,String pass,String phone,int role, String area) throws SQLException{
      String sql="update user_info set user_name ='"+ user +"', password = '"+ pass +"', role_id='"+ role+"' where user_id ="+id+"";
      db.st.executeUpdate(sql);
      String sql2="update customer set cus_name ='"+ name +"', phone = '"+ phone +"', c_area='"+ area+"' where u_id ="+id+"";
      db.st.executeUpdate(sql2);
   }
   void  updateUser(int id, String user, String pass) throws SQLException{
      String sql="update user_info set user_name ='"+ user +"', password = '"+ pass +"' where user_id ="+id+"";
      db.st.executeUpdate(sql);
   }
   void  updateRole(int id) throws SQLException{
      String sql="update user_info set role_id =2 where user_id ="+id+"";
      db.st.executeUpdate(sql);
   }
   void  stopMeter(int id) throws SQLException{
      String sql="update meter_requests set m_state =3 where customer_id ="+id+"";
      db.st.executeUpdate(sql);
   }   
   void  activateMeter(int id) throws SQLException{
      String sql="update meter_requests set m_state =2 where customer_id ="+id+"";
      db.st.executeUpdate(sql);
   }   
   void  updateTriff(int id, int triff) throws SQLException{
      String sql="update customer_triff set triif_id ="+triff+" where customer_id ="+id+"";
      db.st.executeUpdate(sql);
   }
   void  collectPayment(int id) throws SQLException{
      String sql="update bills set collect =1 where b_id ="+id+"";
      db.st.executeUpdate(sql);
   }
   public ResultSet view_customer() throws SQLException{
       String sql="select r_id,units,bill_amount,paid,r_date from reading Left join bills ON r_id=reading_id where customer_id="+customer_id()+" and paid=0";
       db.rs=db.st.executeQuery(sql);
       
       return db.rs;
   }
   public ResultSet view_name() throws SQLException{
       String sql="Select cus_name from customer where c_id="+customer_id()+"";
       db.rs=db.st.executeQuery(sql);
       db.rs.next();
       return db.rs;
   }
   public ResultSet view_users() throws SQLException{
       String sql="Select * from user_info";
       db.rs=db.st.executeQuery(sql);
       return db.rs;
   }
   public ResultSet view_details(int id) throws SQLException{
       String sql="select * from customer where u_id="+id+"";
       db.rs=db.st.executeQuery(sql);
       db.rs.next();
       return db.rs;
   }
   public ResultSet view_req() throws SQLException{
       String sql="select user_id,cus_name,role_id from user_info Inner join customer ON user_id=u_id where role_id=1";
       db.rs=db.st.executeQuery(sql);
       return db.rs;
   }
    public ResultSet view_Area(String area) throws SQLException{
       String sql="Select c_id,cus_name,b_id,units,bill_amount,paid,collect,c_area from customer Inner join reading ON  c_id=customer_id Inner join bills ON r_id=reading_id where c_area='"+area+"'";
       db.rs=db.st.executeQuery(sql);
       return db.rs;
   }
     public ResultSet view_payment() throws SQLException{
       String sql="Select c_id,cus_name,b_id,units,bill_amount from customer Inner join reading ON  c_id=customer_id Inner join bills ON r_id=reading_id where paid=1 AND collect=0";
       db.rs=db.st.executeQuery(sql);
       return db.rs;
   }
     public ResultSet view_triff() throws SQLException{
       String sql="Select c_id,cus_name,m_id,t.triif_id from customer Inner join meter_requests ON customer_id=c_id Inner join customer_triff as t ON t.customer_id=c_id";
       db.rs=db.st.executeQuery(sql);
       return db.rs;
   }
      public ResultSet view_bill() throws SQLException{
       String sql="Select c_id,cus_name,b_id,units,bill_amount,c_area from customer Inner join reading ON  c_id=customer_id Inner join bills ON r_id=reading_id ";
       db.rs=db.st.executeQuery(sql);
       return db.rs;
   }
      public ResultSet view_meter() throws SQLException{
       String sql="Select c_id,cus_name,m_id,state from customer Inner join meter_requests ON customer_id=c_id Inner join status  ON s_id=m_state";
       db.rs=db.st.executeQuery(sql);
       return db.rs;
   }
   public int role_id() throws SQLException{
       String query1="select role_id from user_info where user_id="+user_id()+"";
       db.rs=db.st.executeQuery(query1);
       db.rs.next();
       int i=db.rs.getInt(1);
       return i;
   }
 }
