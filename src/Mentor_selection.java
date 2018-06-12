import java.sql.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Mentor_selection extends Application{
	//*******全局定义*******************************************************************************************
	//*******创建查询、更新等语句的对象和返回结果*******
	private Connection con;
	private Statement sta;
	private ResultSet rs; 
	private ResultSet instrRs; 
	private ResultSet stuRs; 
	private int updatestuRs;
	private int updatestuRs2;
	private Boolean boolrs;
	
	//*******加载驱动程序,获取连接对象连接数据库*******
	private String driverName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/mentor_selection";//指定连接数据库的URL
	private String User = "root"; //指定连接数据库的用户名
	private String Password = "123456";//指定连接数据库的密码
	
	//*******登录页面*******
	private BorderPane loginPane;
	private HBox welcomeHB;
	private HBox nameHB;
	private HBox passwordHB;
	private HBox btnHB;
	private VBox infoVB;
	private TextField useridTF;
	private PasswordField passwordTF;
	private Button loginBtn;
	private Button modifyBtn;
	private Label welLab;
	private Label nameLab;
	private Label passwordLab;
	private Scene loginScene;
	private boolean isLogin = false;
	
	//*******修改密码页面*******
	private BorderPane modifyPwdPane;
	private HBox welPwdHB;
	private HBox namePwdHB;
	private HBox passwordOldPwdHB;
	private HBox passwordPwdHB;	
	private HBox bottomPwdHB;
	private VBox centerPwdVB;
	private TextField modify_useridTF;
	private TextField oldPwdTF;
	private TextField newPwdTF;
	private Button confignBtn;
	private Button cancleBtn;
	private Label welPwdLab;
	private Label namePwdLab;
	private Label passwordOldPwdLab;
	private Label passwordPwdLab;
	private Scene modifyPwdScene;
	private Stage modityStage;
	
	//*******用户信息*******
	private String userName;
	private int userState;
	private int userType;
	
	//*******学生登录成功界面*******
	private TableView<Instructor> studentTable;
	private ObservableList<Instructor> instructorObsList = FXCollections.observableArrayList();
    
	private BorderPane stuNav;
    private HBox searchInstr;
	private HBox stuInfo;
	private VBox studentVB;
	private TextField searchInstrTF;
	private Button searchInstrBtn;
	private Button canclesearchInstrBtn;
	private Button exitStuBtn;
	private Label studentName = new Label();
	private Label studentState = new Label();
	private Label labelInstr;
	private Scene studentScene;
    private Stage studentStage;
    
	private TableColumn<Instructor,String> instrId;
	private TableColumn<Instructor,String> instrName;
	private TableColumn<Instructor,String> gender;
	private TableColumn<Instructor,String> jobTitle;
	private TableColumn<Instructor,String> study;
	private TableColumn<Instructor,String> telephone;
	private TableColumn<Instructor,String> studentNum;
	private TableColumn<Instructor,String> configStuName;
	private TableColumn<Instructor,String> isFull;
	private TableColumn<Instructor,String> action;
	
	//*******教师登录成功界面*******
	private TableView<Student> instructorTable;
	private ObservableList<Student> studentObsList = FXCollections.observableArrayList();
	private BorderPane instrNav;
	private HBox searchStu;
	private HBox instrInfo;
	private VBox instructorVB;
	private TextField searchStuTF;	
	private Button searchStuBtn;
	private Button canclesearchStuBtn;
	private Button exitInstrBtn;
	private Label instructorName = new Label();
	private Label instructorState = new Label();
	private Label labelStu;
	private Scene instructorScene;
	private Stage instructorStage;
	
	private TableColumn<Student, String> stuId;
	private TableColumn<Student, String> stuName;
	private TableColumn<Student, String> stuGender;
	private TableColumn<Student, String> major;
	private TableColumn<Student, String> classroom;
	private TableColumn<Student, String> stuTelephone;
	private TableColumn<Student, String> state;
	private TableColumn<Student, String> instructor;
	private TableColumn<Student, String> stuAction;
	
	//*******管理员登录成功界面*******
	private BorderPane adminNav;
	private HBox adminInfo;
	private HBox adminAct;
	private VBox administratorVB;
	private Button returnAdminBtn;
	private Button alterStuBtn;
	private Button alterInstrBtn;
	private Label adminName = new Label();
	private Label adminState = new Label();
	private Scene administratorScene;
	private Stage administratorStage;
	
	//*******管理员的学生端*******
	private TableView<Student> adminStuTable;
	private ObservableList<Student> adminStuObsList = FXCollections.observableArrayList();
	private BorderPane adminStu;	
	private HBox actionStuHB1;
	private	HBox actionStuHB2;
	private HBox actionStuHB3;
	private HBox StuTop;
	private VBox actionStu;
	private ComboBox<String> combobox;
	private TextField chStuId;
	private TextField chStuName;
	private TextField chStuGender;
	private TextField chStuMajor;
	private TextField chStuClassroom;
	private TextField chStuTelephone;
	private Button addStu;
	private Button deleteStu;
	private Button updateStu;
	private Scene adminStuScene;
	private Stage adminStuStage;
	
	private TableColumn<Instructor,String> admininstrId;
	private TableColumn<Instructor,String> admininstrName;
	private TableColumn<Instructor,String> admingender;
	private TableColumn<Instructor,String> adminjobTitle;
	private TableColumn<Instructor,String> adminstudy;
	private TableColumn<Instructor,String> admintelephone;
	private TableColumn<Instructor,String> adminstudentNum;
	
	//*******管理员的教师端*******
	private TableView<Instructor> adminInstrTable;
	private ObservableList<Instructor> adminInstrObsList = FXCollections.observableArrayList();	
	private BorderPane adminInstr;
    private BorderPane adminInstrtop;
	private HBox actionInstrHB1;
	private HBox actionInstrHB2;
	private HBox actionInstrHB3;
	private HBox actionInstrHB4;
	private VBox actionInstrs;
	private TextField chInstrId;
	private TextField chInstrName;
	private TextField chInstrGender;
	private TextField chInstrJobTitle;
	private TextField chInstrStudy;
	private TextField chInstrTelephone;
	private TextField chInstrStudentNum;
	private Button addInstr;
	private Button deleteInstr;
	private Button updateInstr;
	private Button setstu_num;
	private Scene adminInstrScene;
	private Stage adminInstrStage;
	
	private String[] choose = {"全部","未选","待选","选定"};
	private TableColumn<Student, String> adminstuId;
	private TableColumn<Student, String> adminstuName;
	private TableColumn<Student, String> adminstuGender;
	private TableColumn<Student, String> adminmajor;
	private TableColumn<Student, String> adminclassroom;
	private TableColumn<Student, String> adminstuTelephone;
	private TableColumn<Student, String> adminstuState;
	private TableColumn<Student, String> adminstuInstrid;
	
	//*******开始舞台布局*******************************************************************************************
	@Override 
	public void start(Stage loginStage) {
		//*******登录界面*******************************************************************************************
		loginPane = new BorderPane();
		//登录界面顶部
		welLab = new Label("欢迎登录导师制结对管理系统");
		welcomeHB = new HBox(welLab);
		welcomeHB.setAlignment(Pos.CENTER);
		welcomeHB.setPadding(new Insets(20,0,20,0));
		loginPane.setTop(welcomeHB);
		
		//用户名、密码输入区域
		nameLab = new Label("用户名");
		nameLab.setPrefWidth(50);
		useridTF = new TextField();
		nameHB = new HBox(15,nameLab,useridTF);
		nameHB.setAlignment(Pos.CENTER);
		passwordLab = new Label("密码  ");
		passwordLab.setPrefWidth(50);
		passwordTF = new PasswordField();
		passwordHB = new HBox(15,passwordLab,passwordTF);
		passwordHB.setAlignment(Pos.CENTER);
		infoVB = new VBox(15,nameHB,passwordHB);
		loginPane.setCenter(infoVB);
		
		//选择按钮跳到不同的界面
		loginBtn = new Button("登录");
		loginBtn.setMinWidth(60);
		modifyBtn = new Button("修改密码");
		modifyBtn.setMinWidth(60);
		btnHB = new HBox(15,loginBtn,modifyBtn);
		btnHB.setAlignment(Pos.CENTER);
		btnHB.setPadding(new Insets(20,0,20,0));
		loginPane.setBottom(btnHB);
		
		//设置舞台大小和标题
		loginScene = new Scene(loginPane, 400, 200);
		loginStage.setTitle("登录界面");
		loginStage.setScene(loginScene);
		loginStage.show();
		
		//登录界面的登录按钮事件监听
		loginBtn.setOnAction(e->{
			//连接数据库
			connect();
			//登录
			String loginSql = "select * from user where user_id = '" 
								+ useridTF.getText() + "'and user_password = '" 
								+ passwordTF.getText() +"';";
			try {
				//找到匹配的用户名和密码表示登录成功
				if(login(loginSql)) {
					loginStage.hide();
					//根据用户的类型跳转到不同的界面，跳到学生端，教师端，管理员端)
					if(userType == 1) {
						studentStage.show();
						studentName.setText(userName);
						String stateSql = "select * from student where stu_id = '" + userName + "';";
						studentState.setText(checkStudentState(stateSql));
						showInstructorList();
					}
					//跳到教师端
					else if(userType == 2) {
						instructorStage.show();
						instructorName.setText(userName);
						showStudentList();
					}
					//跳到管理员端
					else if(userType == 3) {
						adminName.setText(userName);
						administratorStage.show();
					}	
					System.out.println("登录成功");	
				}
				else {
					System.out.println("登录失败");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//关闭数据库
			closeConnection();
		});
		
		//登录界面的修改密码按钮事件监听
		modifyBtn.setOnAction(e->{
			loginStage.hide();
			modityStage.show();
		});
		
		//*******修改密码界面*******************************************************************************************
		modifyPwdPane = new BorderPane();
		//修改密码界面顶部
		welPwdLab = new Label("欢迎登录修改密码界面");
		welPwdHB = new HBox(welPwdLab);
		welPwdHB.setAlignment(Pos.CENTER);
		welPwdHB.setPadding(new Insets(20,0,20,0));
		modifyPwdPane.setTop(welPwdHB);

		//用户名、旧密码和新密码输入区域
		namePwdLab = new Label("用户名");
		namePwdLab.setPrefWidth(50);
		modify_useridTF = new TextField();
		namePwdHB = new HBox(15,namePwdLab,modify_useridTF);
		namePwdHB.setAlignment(Pos.CENTER);
		passwordOldPwdLab = new Label("旧密码");
		passwordOldPwdLab.setPrefWidth(50);
		oldPwdTF = new TextField();
		passwordOldPwdHB = new HBox(15,passwordOldPwdLab,oldPwdTF);
		passwordOldPwdHB.setAlignment(Pos.CENTER);
		passwordPwdLab = new Label("新密码");
		passwordPwdLab.setPrefWidth(50);
		newPwdTF = new TextField();
		passwordPwdHB = new HBox(15,passwordPwdLab,newPwdTF);
		passwordPwdHB.setAlignment(Pos.CENTER);
		centerPwdVB = new VBox(15,namePwdHB,passwordOldPwdHB,passwordPwdHB);
		modifyPwdPane.setCenter(centerPwdVB);
		
		//选择按钮实现不同的功能
		confignBtn = new Button("确认修改");
		cancleBtn = new Button("放弃修改");
		bottomPwdHB = new HBox(15,confignBtn,cancleBtn);
		bottomPwdHB.setAlignment(Pos.CENTER);
		bottomPwdHB.setPadding(new Insets(20,0,20,0));
		modifyPwdPane.setBottom(bottomPwdHB);
		
		//设置舞台大小和标题
		modifyPwdScene = new Scene(modifyPwdPane, 400, 250);
		modityStage = new Stage();
		modityStage.setTitle("修改密码界面");
		modityStage.setScene(modifyPwdScene);
		modityStage.hide();
		
		//修改密码界面的确认按钮事件监听
		confignBtn.setOnAction(e->{
			//连接数据库
			connect();
			//修改密码
			String oldSql = "select * from user where user_id = '" 
						+ modify_useridTF.getText() + "'and user_password = '" 
						+ oldPwdTF.getText() +"';";
			//找到匹配的用户名和旧密码即可修改
			try {
				if(modifyPwd(oldSql)) {
					System.out.println("修改成功");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("密码修改成功！");
					alert.showAndWait();
					modityStage.hide();
					loginStage.show();
					
				}
				else {
					modify_useridTF.setText("");
					oldPwdTF.setText("");
					newPwdTF.setText("");
				}	
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//关闭数据库
			closeConnection();
		});
		
		//修改界面的取消按钮事件监听
		cancleBtn.setOnAction(e->{
			loginStage.show();
			modityStage.hide();
		});
		
		//*******学生登录成功界面*******************************************************************************************
		studentTable = new TableView<Instructor>();
		//顶部导航
		stuNav = new BorderPane();
		//根据工号查询教师信息
		searchInstr = new HBox(15);
		searchInstr.setAlignment(Pos.CENTER_LEFT);
		searchInstr.setPadding(new Insets(10,10,0,10));
		labelInstr = new Label("教师工号");
		searchInstrTF = new TextField();
		searchInstrBtn = new Button("查询");
		canclesearchInstrBtn = new Button("取消查询");
		searchInstr.getChildren().addAll(labelInstr,searchInstrTF,searchInstrBtn,canclesearchInstrBtn);
		stuNav.setLeft(searchInstr);
		//根据工号查询教师信息按钮事件监听
		searchInstrBtn.setOnAction(event -> {
			    //连接数据库
			    connect();
				try {
				String instrSql = "select * from instructor where instr_id='" + searchInstrTF.getText() + "';";
				String stuSql = "";
				//教师列表先清空
				instructorObsList.clear();
				sta = con.createStatement();
				//返回查询到的教师
				instrRs = sta.executeQuery(instrSql);
                //对查询结果操作
				while(instrRs.next()){
					stuSql = "select * from student where instr_id = '" + instrRs.getString(1) + "';";
					sta = con.createStatement();
					//返回选择教师工号相符的学生
					stuRs = sta.executeQuery(stuSql);
					//该教师的学生数初始化为0
					int count = 0;
					//存储选择该教师的学生的姓名
					StringBuffer studentList = new StringBuffer();
					while(stuRs.next()) {
						//根据学生查询记录得出人数 并拼接姓名
						count++;
						studentList.append(stuRs.getString(2)+"  ");
					}
					String studentNumStr = instrRs.getInt(7) + "";
					String studentAlrNumStr = count + "";
					//将学生人数和教师允许教的学生人数比较判断是否满额
					String isFullStr = instrRs.getInt(7) <= count ? "满额" : "未满额";
					Instructor instructor = new Instructor(instrRs.getString(1), instrRs.getString(2), 
							instrRs.getString(3), instrRs.getString(4), instrRs.getString(5), 
							instrRs.getString(6),studentNumStr, studentList.toString(),isFullStr);
                    //在教师列表添加记录
					instructorObsList.add(instructor);
				}
				//将列表放入学生登录成功界面的TableView
				studentTable.setItems(instructorObsList);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        });
		
		//取消查询教师信息按钮事件监听
		canclesearchInstrBtn.setOnAction(event -> {
			 try {
				//连接数据库
				connect();
				//显示所有教师的信息
				showInstructorList();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
		
		//学生的个人信息
		exitStuBtn = new Button("退出");
		stuInfo = new HBox(15,studentName,studentState,exitStuBtn);
		stuInfo.setAlignment(Pos.CENTER_RIGHT);
		stuInfo.setPadding(new Insets(10,10,0,10));
		stuNav.setRight(stuInfo);
		
		//创建列对象
		instrId = new TableColumn<Instructor,String>("工号");
		instrId.setMinWidth(100);
		instrName = new TableColumn<Instructor,String>("姓名");
		instrName.setMinWidth(100);
		gender = new TableColumn<Instructor,String>("性别");
		gender.setMinWidth(100);
		jobTitle = new TableColumn<Instructor,String>("职称");
		jobTitle.setMinWidth(100);
		study = new TableColumn<Instructor,String>("研究方向");
		study.setMinWidth(100);
		telephone = new TableColumn<Instructor,String>("电话");
		telephone.setMinWidth(100);
		studentNum = new TableColumn<Instructor,String>("学生数量");
		studentNum.setMinWidth(100);
		configStuName = new TableColumn<Instructor,String>("学生姓名");
		configStuName.setMinWidth(200);
		isFull = new TableColumn<Instructor,String>("是否已满");
		isFull.setMinWidth(100);
		action = new TableColumn<Instructor,String>("操作");
		action.setMinWidth(200);
        //选导师操作按钮列
		Callback<TableColumn<Instructor, String>, TableCell<Instructor, String>> cellFactoryStu
        = new Callback<TableColumn<Instructor, String>, TableCell<Instructor, String>>() {
			 public TableCell call(final TableColumn<Instructor, String> param) {
			     final TableCell<Instructor, String> cell = new TableCell<Instructor, String>() {
			    	 final Button btn = new Button("选定");
		             @Override
		             public void updateItem(String item, boolean empty) {
		                 super.updateItem(item, empty);
		                 if (empty) {
		                     setGraphic(null);
		                     setText(null);
		                 } else {
		                	 //如果已经被选定了 按钮变灰
		                	 if(userState == 3) {
	                    	 		btn.setDisable(true);
		                	    }
		                	 else {
		                		//选导师操作按钮事件监听
		                	     btn.setOnAction(event -> {
		                	 	 Instructor instructor = getTableView().getItems().get(getIndex());
		                    	 	//学生状态未选
		                    	 	if(userState == 1) {
		                    	 		//连接数据库
		                    	 		connect();
		                    	 		try {
		                    	 		String stu_state_update = "update student set state_id=2 where stu_id= '" + userName + "';";
		                    	 		String stu_instr_id_update="update student set instr_id='" + instructor.getInstrId() + "' where stu_id= '" + userName + "';";
		                        		sta = con.createStatement();
		                        		//更新学生的状态为待选 并补充所选导师的工号
		                    			updatestuRs = sta.executeUpdate(stu_state_update);
		                    			updatestuRs2=sta.executeUpdate(stu_instr_id_update);
		                    			showInstructorList();
		                    			studentState.setText("待选");
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
		                    	 	}
		                    	 	//学生状态待选
		                    	 	else if(userState == 2) {
		                    	 		//连接数据库
		                    	 		connect();
		                    	 		try {
		                    	 		String stu_instr_id_update="update student set instr_id='" + instructor.getInstrId() + "' where stu_id= '" + userName + "';";
		                    			sta = con.createStatement();
		                    			//更新学所选导师的工号
		                    			updatestuRs2=sta.executeUpdate(stu_instr_id_update);
		                    			showInstructorList();
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
		                    	 	}
		                     });
		                	}
		                     setGraphic(btn);
		                     setText(null);
		                 }
		             }
			     	};
			     return cell;
			 }
		 };
		action.setCellFactory(cellFactoryStu);
		
		//把列对象添加到表视图
		studentTable.getColumns().addAll(instrId,instrName,gender,jobTitle,study,telephone,studentNum,configStuName,isFull,action);
		studentTable.setItems(instructorObsList);
		studentTable.setPrefHeight(400);
        
		//把数据列表和列对象建立关联
		instrId.setCellValueFactory(new PropertyValueFactory<Instructor, String>("instrId"));
		instrName.setCellValueFactory(new PropertyValueFactory<Instructor, String>("instrName"));
		gender.setCellValueFactory(new PropertyValueFactory<Instructor, String>("gender"));
		jobTitle.setCellValueFactory(new PropertyValueFactory<Instructor, String>("jobTitle"));
		study.setCellValueFactory(new PropertyValueFactory<Instructor, String>("study"));
		telephone.setCellValueFactory(new PropertyValueFactory<Instructor, String>("telephone"));
		studentNum.setCellValueFactory(new PropertyValueFactory<Instructor, String>("studentNum"));
		configStuName.setCellValueFactory(new PropertyValueFactory<Instructor, String>("configStuName"));
		isFull.setCellValueFactory(new PropertyValueFactory<Instructor, String>("isFull"));
		action.setCellValueFactory(new PropertyValueFactory<Instructor, String>("action"));
        
		//设置舞台大小和标题
		studentVB = new VBox(15,stuNav,studentTable);
		studentScene = new Scene(studentVB, 1200, 400);
		studentStage = new Stage();
		studentStage.setTitle("学生界面");
		studentStage.setMaxWidth(1200);
		studentStage.setScene(studentScene);
		studentStage.hide();
		
		//*******教师登录成功界面*******************************************************************************************		
		instructorTable = new TableView<Student>();
		//顶部导航
		instrNav = new BorderPane();
		//根据学号查询学生信息
		searchStu = new HBox(15);
		searchStu.setAlignment(Pos.CENTER_LEFT);
		searchStu.setPadding(new Insets(10,10,0,10));
		labelStu = new Label("学生学号");
		searchStuTF = new TextField();
		searchStuBtn = new Button("查询");
		canclesearchStuBtn = new Button("取消查询");
		searchStu.getChildren().addAll(labelStu,searchStuTF,searchStuBtn,canclesearchStuBtn);
		instrNav.setLeft(searchStu);
		//根据学号查询学生信息按钮事件监听
		searchStuBtn.setOnAction(event -> {
			//连接数据库
			connect();
    	 	String stuSearch = "select * from student where instr_id = '" + userName + "' and stu_id='"+searchStuTF.getText()+"';";
			try {
				sta = con.createStatement();
				//返回查询到的学生
			    stuRs = sta.executeQuery(stuSearch);
			    studentObsList.clear();
			    while(stuRs.next()){
					String state_show;
					//根据状态数字 转换为中文状态显示
					if(stuRs.getInt(7)==1)state_show="未选";
					else if(stuRs.getInt(7)==2)state_show="待选";
					else state_show="选定";
					Student student = new Student(stuRs.getString(1),stuRs.getString(2),stuRs.getString(3),stuRs.getString(4),
							                      stuRs.getString(5),stuRs.getString(6),state_show,stuRs.getString(8));
					//在学生列表添加记录
					studentObsList.add(student);
				  }
			  //将列表放入教师登录成功界面的TableView
				instructorTable.setItems(studentObsList);	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}		 
        });
		
		//取消查询学生信息按钮事件监听
		canclesearchStuBtn.setOnAction(event -> {
			 try {
				connect();
				showStudentList();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
		
		//教师的个人信息
		instrInfo = new HBox(15);
		instrInfo.setAlignment(Pos.CENTER_RIGHT);
		instrInfo.setPadding(new Insets(10,10,0,10));
		exitInstrBtn = new Button("退出");
		instrInfo.getChildren().addAll(instructorName,instructorState,exitInstrBtn);
		instrNav.setRight(instrInfo);
		
		//创建列对象
		stuId = new TableColumn<Student,String>("学号");
		stuId.setMinWidth(100);
		stuName = new TableColumn<Student,String>("姓名");
		stuName.setMinWidth(100);
		stuGender = new TableColumn<Student,String>("性别");
		stuGender.setMinWidth(50);
		major = new TableColumn<Student,String>("专业");
		major.setMinWidth(200);
		classroom = new TableColumn<Student,String>("班级");
		classroom.setMinWidth(100);
		stuTelephone = new TableColumn<Student,String>("电话");
		stuTelephone.setMinWidth(100);
		state = new TableColumn<Student,String>("状态");
		state.setMinWidth(50);
		instructor = new TableColumn<Student,String>("导师");
		instructor.setMinWidth(100);
		stuAction = new TableColumn<Student,String>("操作");
		stuAction.setMinWidth(200);
		//选学生操作按钮列
		Callback<TableColumn<Student, String>, TableCell<Student, String>> cellFactoryInstr
        = new Callback<TableColumn<Student, String>, TableCell<Student, String>>() {
			 public TableCell call(final TableColumn<Student, String> param) {
			     final TableCell<Student, String> cell = new TableCell<Student, String>() {
		             Button selectBtn = new Button("选定");
		             Button obsolescenceBtn = new Button("淘汰");
		             HBox instroperation = new HBox(15,selectBtn,obsolescenceBtn);
		             @Override
		             public void updateItem(String item, boolean empty) {
		                 super.updateItem(item, empty);
		                 if (empty) {
		                     setGraphic(null);
		                     setText(null);
		                 } else {
		                	    //先将按钮都禁止操作
		                	    selectBtn.setDisable(true);
		                	    obsolescenceBtn.setDisable(true);
		                	 	Student student = getTableView().getItems().get(getIndex());
             		    		if(student.getInstructor() != null) {
             		    			if(student.getState()!="选定") {
             		    				//如果学生状态不是选定 再恢复按钮
             		    				selectBtn.setDisable(false);
             		    				obsolescenceBtn.setDisable(false);
             			        	}
             		    		}	
             		    		//选定学生操作按钮事件监听
             		    		selectBtn.setOnAction(event -> {
	                	    			    //连接数据库
	                	    			    connect();
			                    	 		try {
			                    	 		String stu_state_update = "update student set state_id=3 where stu_id= '" + student.getStuId() + "';";
			                    			sta = con.createStatement();
			                    			//更新被操作学生的状态为选定
			                    			updatestuRs = sta.executeUpdate(stu_state_update);
			                    			showStudentList();
											} catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
		                     });
             		    		obsolescenceBtn.setOnAction(event -> {
	                	    			//连接数据库
	                	    			connect();
	                	    			try {
			                    	 		String stu_state_update = "update student set state_id=1 where stu_id= '" + student.getStuId() + "';";
			                    	 		String stu_instr_id_update="update student set instr_id=null where stu_id= '" + student.getStuId() + "';";
			                    			sta = con.createStatement();
			                    			//更新学生状态为未选并清空所选导师的工号
			                    			updatestuRs = sta.executeUpdate(stu_state_update);
			                    			updatestuRs2=sta.executeUpdate(stu_instr_id_update);
			                    			showStudentList();
											} catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
		                     });
		                     setGraphic(instroperation);
		                     setText(null);
		                 }
		             }
			     	};
			     return cell;
			 }
		 };
		stuAction.setCellFactory(cellFactoryInstr);
		
		//把列对象添加到表视图
		instructorTable.getColumns().addAll(stuId,stuName,stuGender,major,classroom,stuTelephone,state,instructor,stuAction);
		instructorTable.setItems(studentObsList);
		instructorTable.setPrefHeight(400);
		
		//把数据列表和列对象建立关联
		stuId.setCellValueFactory(new PropertyValueFactory<Student, String>("stuId"));
		stuName.setCellValueFactory(new PropertyValueFactory<Student, String>("stuName"));
		stuGender.setCellValueFactory(new PropertyValueFactory<Student, String>("stuGender"));
		major.setCellValueFactory(new PropertyValueFactory<Student, String>("major"));
		classroom.setCellValueFactory(new PropertyValueFactory<Student, String>("classroom"));
		stuTelephone.setCellValueFactory(new PropertyValueFactory<Student, String>("stuTelephone"));
		state.setCellValueFactory(new PropertyValueFactory<Student,String>("state"));
		instructor.setCellValueFactory(new PropertyValueFactory<Student, String>("instructor"));
		
		//设置舞台大小和标题
		instructorVB = new VBox(15,instrNav,instructorTable);
		instructorScene = new Scene(instructorVB, 1050, 420);
		instructorStage = new Stage();
		instructorStage.setTitle("教师界面");
		instructorStage.setMaxWidth(1050);
		instructorStage.setScene(instructorScene);
		instructorStage.hide();
		
		//*******管理员界面*******************************************************************************************
		//顶部导航
		adminNav = new BorderPane();
		//管理员的个人信息
		returnAdminBtn = new Button("退出");
		adminInfo = new HBox(10,adminName,adminState,returnAdminBtn);
		adminInfo.setAlignment(Pos.CENTER_RIGHT);
		adminInfo.setPadding(new Insets(10,10,0,10));
		adminNav.setRight(adminInfo);
		
		//*******基本操作页面*******
		adminAct = new HBox(15);
		adminAct.setAlignment(Pos.CENTER);
		adminAct.setPadding(new Insets(40,10,0,10));
	    alterStuBtn = new Button("学生信息操作");
		alterInstrBtn = new Button("教师信息操作");
		adminAct.getChildren().addAll(alterStuBtn,alterInstrBtn);
		administratorVB = new VBox(15,adminNav,adminAct);
		administratorScene = new Scene(administratorVB, 400, 200);
		administratorStage = new Stage();
		administratorStage.setTitle("管理员界面");
		administratorStage.setMaxWidth(400);
		administratorStage.setScene(administratorScene);
		administratorStage.hide();		
		//管理员选择操作对象为学生的按钮事件监听
		alterStuBtn.setOnAction(e->{
			adminStuStage.show();
			try {
				showAdminStudentList();
			} catch (SQLException e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		//管理员选择操作对象为教师的按钮事件监听
		alterInstrBtn.setOnAction(e->{
			adminInstrStage.show();
			try {
				showAdminInstructorList();
			} catch (SQLException e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
						
		//*******查看老师列表*******	
		adminInstrTable = new TableView<Instructor>();
		adminInstrTable.setPrefHeight(400);
		adminInstrTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		//给表视图注册鼠标事件
		adminInstrTable.setOnMouseClicked(new InstrMouseClickedListener());
		//创建列对象
		admininstrId = new TableColumn<Instructor,String>("工号");
		admininstrId.setMinWidth(100);
		admininstrName = new TableColumn<Instructor,String>("姓名");
		admininstrName.setMinWidth(100);
		admingender = new TableColumn<Instructor,String>("性别");
		admingender.setMinWidth(100);
		adminjobTitle = new TableColumn<Instructor,String>("职称");
		adminjobTitle.setMinWidth(100);
		adminstudy = new TableColumn<Instructor,String>("研究方向");
		adminstudy.setMinWidth(100);
		admintelephone = new TableColumn<Instructor,String>("电话");
		admintelephone.setMinWidth(100);
		adminstudentNum = new TableColumn<Instructor,String>("学生数量");
		adminstudentNum.setMinWidth(100);
		//把列对象添加到表视图
		adminInstrTable.getColumns().addAll(admininstrId,admininstrName,admingender,adminjobTitle,adminstudy,admintelephone,adminstudentNum);
		//把数据列表和列对象建立关联
		admininstrId.setCellValueFactory(new PropertyValueFactory<Instructor, String>("instrId"));
		admininstrName.setCellValueFactory(new PropertyValueFactory<Instructor, String>("instrName"));
		admingender.setCellValueFactory(new PropertyValueFactory<Instructor, String>("gender"));
		adminjobTitle.setCellValueFactory(new PropertyValueFactory<Instructor, String>("jobTitle"));
		adminstudy.setCellValueFactory(new PropertyValueFactory<Instructor, String>("study"));
		admintelephone.setCellValueFactory(new PropertyValueFactory<Instructor, String>("telephone"));
		adminstudentNum.setCellValueFactory(new PropertyValueFactory<Instructor, String>("studentNum"));
		
		//*******管理员查看编辑教师信息*******
		Label InstrIdlabel=new Label("工号：");
		Label InstrNamelabel=new Label("姓名:         ");
		Label InstrGenderlabel=new Label("性别：");
		Label InstrJobTitlelabel=new Label("职称：");
		Label InstrStudylabel=new Label("研究方向：");
		Label InstrTelephonelabel=new Label("电话：");
		Label InstrStudentNumlabel=new Label("学生数量：");
		
		chInstrId = new TextField();
		chInstrId.setMaxWidth(100);
		chInstrName = new TextField();
		chInstrName.setMaxWidth(100);
		chInstrGender = new TextField();
		chInstrGender.setMaxWidth(100);
		chInstrJobTitle = new TextField();
		chInstrJobTitle.setMaxWidth(100);
		chInstrStudy = new TextField();
		chInstrStudy.setMaxWidth(100);
		chInstrTelephone = new TextField();
		chInstrTelephone.setMaxWidth(100);
		chInstrStudentNum = new TextField();
		chInstrStudentNum.setMaxWidth(70);
		
		addInstr = new Button("添加");
		addInstr.setPrefWidth(50);
		addInstr.setOnAction(new InstrAddListener());
		deleteInstr = new Button("删除");
		deleteInstr.setPrefWidth(50);
		deleteInstr.setOnAction(new InstrDeleteListener());
		updateInstr = new Button("修改");
		updateInstr.setPrefWidth(50);
		updateInstr.setOnAction(new InstrUpdateListener());
		setstu_num= new Button("修改");
		setstu_num.setPrefWidth(50);
		setstu_num.setOnAction(new Stu_numListener());
		
		//添加输入框显示选中行的信息 按钮可进行相应操作
		actionInstrHB1 = new HBox(5,InstrIdlabel,chInstrId,InstrNamelabel,chInstrName,InstrGenderlabel,chInstrGender);
		actionInstrHB2 = new HBox(5,InstrJobTitlelabel,chInstrJobTitle,InstrStudylabel,chInstrStudy,InstrTelephonelabel,chInstrTelephone);
		actionInstrHB3 = new HBox(10,addInstr,deleteInstr,updateInstr);
		adminInstrtop= new BorderPane();
		adminInstrtop.setLeft(actionInstrHB2);
		adminInstrtop.setRight(actionInstrHB3);
		actionInstrHB4=new HBox(15,InstrStudentNumlabel,chInstrStudentNum,setstu_num);
		actionInstrs = new VBox(10,actionInstrHB1,adminInstrtop,new Separator(),actionInstrHB4);
		actionInstrs.setPadding(new Insets(10,10,10,10));
		actionInstrHB4.setPadding(new Insets(10,0,10,0));
		actionInstrs.setAlignment(Pos.CENTER);
		adminInstr = new BorderPane();
		adminInstr.setCenter(adminInstrTable);
		adminInstr.setBottom(actionInstrs);
		
		//设置舞台大小和标题
		adminInstrScene = new Scene(adminInstr, 800, 420);
		adminInstrStage = new Stage();
		adminInstrStage.setTitle("管理员端——教师信息");
		adminInstrStage.setMaxWidth(800);
		adminInstrStage.setScene(adminInstrScene);
		adminInstrStage.hide();
		
		//*******查看学生列表*******
		adminStuTable = new TableView<Student>();
		adminStuTable.setPrefHeight(400);
		adminStuTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		//给表视图注册鼠标事件
		adminStuTable.setOnMouseClicked(new StuMouseClickedListener());
		//创建列对象			
		adminstuId = new TableColumn<Student,String>("学号");
		adminstuId.setMinWidth(100);
		adminstuName = new TableColumn<Student,String>("姓名");
		adminstuName.setMinWidth(100);
		adminstuGender = new TableColumn<Student,String>("性别");
		adminstuGender.setMinWidth(50);
		adminmajor = new TableColumn<Student,String>("专业");
		adminmajor.setMinWidth(200);
		adminclassroom = new TableColumn<Student,String>("班级");
		adminclassroom.setMinWidth(100);
		adminstuTelephone = new TableColumn<Student,String>("电话");
		adminstuTelephone.setMinWidth(100);
		adminstuState = new TableColumn<Student,String>("状态");
		adminstuState.setMinWidth(100);
		adminstuInstrid = new TableColumn<Student,String>("导师");
		adminstuInstrid.setMinWidth(100);
		//把列对象添加到表视图
		adminStuTable.getColumns().addAll(adminstuId,adminstuName,adminstuGender,adminmajor,adminclassroom,adminstuTelephone,adminstuState,adminstuInstrid);
		//把数据列表和列对象建立关联
		adminstuId.setCellValueFactory(new PropertyValueFactory<Student, String>("stuId"));
		adminstuName.setCellValueFactory(new PropertyValueFactory<Student, String>("stuName"));
		adminstuGender.setCellValueFactory(new PropertyValueFactory<Student, String>("stuGender"));
		adminmajor.setCellValueFactory(new PropertyValueFactory<Student, String>("major"));
		adminclassroom.setCellValueFactory(new PropertyValueFactory<Student, String>("classroom"));
		adminstuTelephone.setCellValueFactory(new PropertyValueFactory<Student, String>("stuTelephone"));
		adminstuState.setCellValueFactory(new PropertyValueFactory<Student, String>("state"));
		adminstuInstrid.setCellValueFactory(new PropertyValueFactory<Student, String>("instructor"));
		
		//*******管理员查看编辑学生信息*******
		chStuId = new TextField();
		chStuId.setMaxWidth(150);
		chStuName = new TextField();
		chStuName.setMaxWidth(150);
		chStuGender = new TextField();
		chStuGender.setMaxWidth(150);
		chStuMajor = new TextField();
		chStuMajor.setMaxWidth(150);
		chStuClassroom = new TextField();
		chStuClassroom.setMaxWidth(150);
		chStuTelephone = new TextField();
		chStuTelephone.setMaxWidth(150);
		Label Stuidlabel=new Label("学号:");
		Label StuNamelabel=new Label("姓名:");
		Label StuGenderlabel=new Label("性别:");
		Label StuMajorlabel=new Label("专业:");
		Label StuClassroomlabel=new Label("班级:");
        Label StuTelephonelabel=new Label("电话:");
        Label StuStatelabel=new Label("学生状态");
		addStu = new Button("添加");
		addStu.setPrefWidth(50);
		addStu.setOnAction(new StuAddListener());
		deleteStu = new Button("删除");
		deleteStu.setPrefWidth(50);
		deleteStu.setOnAction(new StuDeleteListener());
		updateStu = new Button("修改");
		updateStu.setPrefWidth(50);
		updateStu.setOnAction(new StuUpdateListener());
		combobox = new ComboBox<>();
		combobox.setPrefWidth(100);
		combobox.setValue("全部");
		ObservableList<String> items = FXCollections.observableArrayList(choose);
		combobox.setItems(items);
		
		//*******通过学生状态筛选学生*******
		combobox.setOnAction(e->{
			if(items.indexOf(combobox.getValue()) == 0) {
				try {
					showAdminStudentList();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(items.indexOf(combobox.getValue()) == 1) {
				    //连接数据库
					connect();
					String stuSql = "select * from student where state_id=1;";
					try {
						sta = con.createStatement();
						//筛选出学生状态为未选的学生
					    stuRs = sta.executeQuery(stuSql);
					    //清空学生列表
					    adminStuObsList.clear();
					    while(stuRs.next()){
						String state_show;
						if(stuRs.getInt(7)==1)state_show="未选";
						else if(stuRs.getInt(7)==2)state_show="待选";
						else state_show="选定";
						Student student2 = new Student(stuRs.getString(1),stuRs.getString(2),stuRs.getString(3),stuRs.getString(4),stuRs.getString(5),stuRs.getString(6),state_show,stuRs.getString(8));
						adminStuObsList.add(student2);
					}
					//将列表放入管理员编辑学生信息界面的TableView
					adminStuTable.setItems(adminStuObsList);
					closeConnection();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
			else if(items.indexOf(combobox.getValue()) == 2) {
				//连接数据库
				connect();
				String stuSql = "select * from student where state_id=2;";
				try {
					sta = con.createStatement();
					//筛选出学生状态为待选的学生
				    stuRs = sta.executeQuery(stuSql);
				    adminStuObsList.clear();
				    while(stuRs.next()){
					String state_show;
					if(stuRs.getInt(7)==1)state_show="未选";
					else if(stuRs.getInt(7)==2)state_show="待选";
					else state_show="选定";
					Student student2 = new Student(stuRs.getString(1),stuRs.getString(2),stuRs.getString(3),stuRs.getString(4),stuRs.getString(5),stuRs.getString(6),state_show,stuRs.getString(8));
					adminStuObsList.add(student2);
				}
				//将列表放入管理员编辑学生信息界面的TableView
				adminStuTable.setItems(adminStuObsList);
				closeConnection();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				//连接数据库
				connect();
				String stuSql = "select * from student where state_id=3;";
				try {
					sta = con.createStatement();
					//筛选出学生状态为待选的学生
				    stuRs = sta.executeQuery(stuSql);
				    adminStuObsList.clear();
				    while(stuRs.next()){
					String state_show;
					if(stuRs.getInt(7)==1)state_show="未选";
					else if(stuRs.getInt(7)==2)state_show="待选";
					else state_show="选定";
					Student student2 = new Student(stuRs.getString(1),stuRs.getString(2),stuRs.getString(3),stuRs.getString(4),stuRs.getString(5),stuRs.getString(6),state_show,stuRs.getString(8));
					adminStuObsList.add(student2);
				}
				//将列表放入管理员编辑学生信息界面的TableView
				adminStuTable.setItems(adminStuObsList);
				closeConnection();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		//添加输入框显示选中行的信息 按钮可进行相应操作
		actionStuHB1 = new HBox(5,Stuidlabel,chStuId,StuNamelabel,chStuName,StuGenderlabel,chStuGender);
		actionStuHB2 = new HBox(5,StuMajorlabel,chStuMajor,StuClassroomlabel,chStuClassroom,StuTelephonelabel,chStuTelephone);
		actionStuHB3 = new HBox(15,addStu,deleteStu,updateStu);
		StuTop=new HBox(5,StuStatelabel,combobox);
		StuTop.setPadding(new Insets(10,10,10,10));
		actionStu = new VBox(10,actionStuHB1,actionStuHB2,actionStuHB3);
		actionStuHB1.setAlignment(Pos.CENTER);
		actionStuHB2.setAlignment(Pos.CENTER);
		actionStuHB3.setAlignment(Pos.CENTER);
		StuTop.setAlignment(Pos.BASELINE_LEFT);
		actionStu.setAlignment(Pos.CENTER);
		actionStu.setPadding(new Insets(10,10,10,10));
		adminStu = new BorderPane();
		adminStu.setTop(StuTop);
		adminStu.setCenter(adminStuTable);
		adminStu.setBottom(actionStu);
		
		//设置舞台大小和标题
		adminStuScene = new Scene(adminStu, 1000, 420);
		adminStuStage = new Stage();
		adminStuStage.setTitle("管理员端——学生信息");
		adminStuStage.setMaxWidth(1000);
		adminStuStage.setScene(adminStuScene);
		adminStuStage.hide();
		
		//*******退出按钮*******
		//学生退出按钮事件监听
		exitStuBtn.setOnAction(e->{
			useridTF.setText("");
			passwordTF.setText("");
			studentStage.hide();
			loginStage.show();
		});
		
		//老师退出按钮事件监听
		exitInstrBtn.setOnAction(e->{
			useridTF.setText("");
			passwordTF.setText("");
			instructorStage.hide();
			loginStage.show();
		});
		
		//管理员退出按钮事件监听
		returnAdminBtn.setOnAction(e->{
			useridTF.setText("");
			passwordTF.setText("");
			administratorStage.hide();
			loginStage.show();
		});		
   }
	
	//*******函数定义*******************************************************************************************
	//连接数据库
	public void connect() {
		try {
			Class.forName(driverName);
			// 创建数据库连接
			con = DriverManager.getConnection(url, User, Password);
			System.out.println("connect success");
			if(con == null) {
				System.out.println("connect is null");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("connect fail" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("connect fail" + e.getMessage());
		}
	}
	//关闭数据库
	public void closeConnection() {
		try {
			if (con != null) {
				if (sta != null) {
					if (rs != null) {
						rs.close();
					}
					con.close();
				}
			}
			System.out.println("close connect success");
		}
		catch (SQLException e) {
			System.out.println("close connect fail" + e.getMessage());
		}
	}
	//判断是否成功登录
    public boolean login(String sql) throws SQLException {
		try {
			sta = con.createStatement();
			//返回查询的学生、教师或管理员
			rs = sta.executeQuery(sql.toString());
			if(rs.next()) {
				//记录类型和用户名
				userType = rs.getInt(3);
				userName = rs.getString(1);
				return true;
			}
			//查询失败则弹出提醒框
			else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("您输入的用户名或密码不正确！");
				alert.showAndWait();
				passwordTF.setText("");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("connect fail" + e.getMessage());
			return false;
		}	
	}
   //修改密码
	public boolean modifyPwd(String oldSql) throws SQLException{
		//如果查询成功就更新密码
		if(login(oldSql)) {
			String sql = "update user set user_password = '" +  newPwdTF.getText() 
							+ "' where user_id = '" + modify_useridTF.getText() + "';";
			sta = con.createStatement();
			int numOfModify = sta.executeUpdate(sql);
			return true;
		}
		else {
			return false;
		}
		
	}
	//显示教师列表
	public void showInstructorList() throws SQLException {	
		String instrSql = "select * from instructor;";
		String stuSql = "";
		instructorObsList.clear();
		sta = con.createStatement();
		//返回查询到的教师
		instrRs = sta.executeQuery(instrSql);
		//对查询结果操作
		while(instrRs.next()){
			stuSql = "select * from student where instr_id = '" + instrRs.getString(1) + "';";
			sta = con.createStatement();
			stuRs = sta.executeQuery(stuSql);
			//该教师的学生数初始化为0
			int count = 0;
			//存储选择该教师的学生的姓名
			StringBuffer studentList = new StringBuffer();
			while(stuRs.next()) {
				//根据学生查询记录得出人数 并拼接姓名
				count++;
				studentList.append(stuRs.getString(2)+"  ");
			}
			String studentNumStr = instrRs.getInt(7) + "";
			String studentAlrNumStr = count + "";
			//将学生人数和教师允许教的学生人数比较判断是否满额
			String isFullStr = instrRs.getInt(7) <= count ? "满额" : "未满额";
			Instructor instructor = new Instructor(instrRs.getString(1), instrRs.getString(2), 
					instrRs.getString(3), instrRs.getString(4), instrRs.getString(5), 
					instrRs.getString(6),studentNumStr, studentList.toString(),isFullStr);
			//在教师列表添加记录
			instructorObsList.add(instructor);
		}
		//将列表放入TableView
		studentTable.setItems(instructorObsList);
	}
	//管理员端显示教师列表
	public void showAdminInstructorList() throws SQLException {	
		connect();
		String instrSql = "select * from instructor;";
		String stuSql = "";
		adminInstrObsList.clear();
		sta = con.createStatement();
		//返回查询到的教师
		instrRs = sta.executeQuery(instrSql);
		while(instrRs.next()){
			stuSql = "select * from student where instr_id = '" + instrRs.getString(1) + "';";
			sta = con.createStatement();
			stuRs = sta.executeQuery(stuSql);
			//该教师的学生数初始化为0
			int count = 0;
			while(stuRs.next()) {
				//根据学生查询记录得出人数
				count++;
			}
			String studentNumStr = instrRs.getInt(7)+"";
			String studentAlrNumStr = count + "";
			Instructor instructor1 = new Instructor(instrRs.getString(1), instrRs.getString(2),
			instrRs.getString(3), instrRs.getString(4), instrRs.getString(5),instrRs.getString(6),studentNumStr);
			//在管理员界面的教师列表添加记录
			adminInstrObsList.add(instructor1);
		}
		//将列表放入TableView
		adminInstrTable.setItems(adminInstrObsList);
		closeConnection();
	}
	//显示学生列表
	public void showStudentList() throws SQLException {
		String stuSql = "select * from student where instr_id = '" + userName + "';";
		sta = con.createStatement();
		//返回查询到的学生
		stuRs = sta.executeQuery(stuSql);
		studentObsList.clear();
		while(stuRs.next()){
			//根据状态数字 转换为中文状态显示
			String state_show;
			if(stuRs.getInt(7)==1)state_show="未选";
			else if(stuRs.getInt(7)==2)state_show="待选";
			else state_show="选定";
			Student student = new Student(stuRs.getString(1),stuRs.getString(2),stuRs.getString(3),stuRs.getString(4),
					stuRs.getString(5),stuRs.getString(6),state_show,stuRs.getString(8));
			//在学生列表添加记录
			studentObsList.add(student);
		}
		//将列表放入TableView
		instructorTable.setItems(studentObsList);
	}
	//显示管理员端的学生列表
	public void showAdminStudentList() throws SQLException {
		connect();
		String stuSql = "select * from student;";
		sta = con.createStatement();
		//返回查询到的学生
		stuRs = sta.executeQuery(stuSql);
		adminStuObsList.clear();
		while(stuRs.next()){
			//根据状态数字 转换为中文状态显示
			String state_show;
			if(stuRs.getInt(7)==1)state_show="未选";
			else if(stuRs.getInt(7)==2)state_show="待选";
			else state_show="选定";
			Student student2 = new Student(stuRs.getString(1),stuRs.getString(2),stuRs.getString(3),stuRs.getString(4),stuRs.getString(5),stuRs.getString(6),state_show,stuRs.getString(8));
			//在学生列表添加记录
			adminStuObsList.add(student2);
		}
		//将列表放入TableView
		adminStuTable.setItems(adminStuObsList);
		closeConnection();
	}
	//根据数据库学生状态的数字转换为中文显示
	public String checkStudentState(String stateSql) throws SQLException {
		String stateStr = "";
		sta = con.createStatement();
		stuRs = sta.executeQuery(stateSql);
		while(stuRs.next()) {
			userState = stuRs.getInt(7);
			if(stuRs.getInt(7) == 1) {
				stateStr = "未选";
				
			}
			else if(stuRs.getInt(7) == 2) {
				stateStr = "待选";
			}
			else if(stuRs.getInt(7) == 3) {
				stateStr = "选定";
			}
		}
		return stateStr;
	}
	
	//*******事件监听（响应）*******************************************************************************************
	public class StuMouseClickedListener implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent arg0) {
			// 得到用户选择的记录
			int selectedRow = adminStuTable.getSelectionModel().getSelectedIndex();
			// 如果确实选取了某条记录
			if(selectedRow!=-1){
				// 获取选择的记录
				Student abcObj = adminStuObsList.get(selectedRow);
				// 把用户选择的记录中的内容分别添加到对应的文本框中
				chStuId.setText(abcObj.getStuId());
				chStuName.setText(abcObj.getStuName());
				chStuGender.setText(abcObj.getStuGender());
				chStuMajor.setText(abcObj.getMajor());
				chStuClassroom.setText(abcObj.getClassroom());
				chStuTelephone.setText(abcObj.getStuTelephone());
			// if语句结束
			}
		}
	}
	public class InstrMouseClickedListener implements EventHandler<MouseEvent> {
			@Override
			public void handle(MouseEvent arg0) {
				// 得到用户选择的记录
				int selectedRow = adminInstrTable.getSelectionModel().getSelectedIndex();
				// 如果确实选取了某条记录
				if(selectedRow!=-1){
					// 获取选择的记录
					Instructor abcObj = adminInstrObsList.get(selectedRow);
					// 把用户选择的记录中的内容分别添加到对应的文本框中
					chInstrId.setText(abcObj.getInstrId());
					chInstrName.setText(abcObj.getInstrName());
					chInstrGender.setText(abcObj.getGender());
					chInstrJobTitle.setText(abcObj.getJobTitle());
					chInstrStudy.setText(abcObj.getStudy());
					chInstrTelephone.setText(abcObj.getTelephone());
					chInstrStudentNum.setText(abcObj.getStudentNum());
				// if语句结束
				}
			}
		}

	//******点击“添加”按钮的事件监听类******
	public class StuAddListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			connect();
			try {
    	 		String adminstu_add = "insert into student values('" + chStuId.getText() + "','" + chStuName.getText() + "','" + chStuGender.getText() + "','" + chStuMajor.getText() + "','" + chStuClassroom.getText() + "','" + chStuTelephone.getText() + "',1,null);";
    			String chStu=chStuId.getText();
    	 		sta = con.createStatement();
    	 		//添加学生信息记录
    			boolrs = sta.execute(adminstu_add);
    			//同时清空输入框
    			showAdminStudentList();
    			chStuId.setText("");
    			chStuName.setText("");
    			chStuGender.setText("");
    			chStuMajor.setText("");
    			chStuClassroom.setText("");
    			chStuTelephone.setText("");
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Information Dialog");
    			alert.setHeaderText(null);
    			alert.setContentText("您已增加一条学号为" + chStu + "的学生信息记录！");
    			alert.showAndWait();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public class InstrAddListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			connect();
			try {
    	 		String admininstr_add = "insert into instructor values('" + chInstrId.getText() + "','" + chInstrName.getText() + "','" + chInstrGender.getText() + "','" + chInstrJobTitle.getText() + "','" + chInstrStudy.getText() + "','" + chInstrTelephone.getText() + "','" + chInstrStudentNum.getText() + "');";
    			String chInstr=chInstrId.getText();
    	 		sta = con.createStatement();
    	 		//添加教师信息记录
    			boolrs = sta.execute(admininstr_add);
    			showAdminInstructorList();
    			//同时清空输入框
    			chInstrId.setText("");
    			chInstrName.setText("");
    			chInstrGender.setText("");
    			chInstrJobTitle.setText("");
    			chInstrStudy.setText("");
    			chInstrTelephone.setText("");
    			chInstrStudentNum.setText("");
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Information Dialog");
    			alert.setHeaderText(null);
    			alert.setContentText("您已增加一条工号为" + chInstr + "的教师信息记录！");
    			alert.showAndWait();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	// ******点击“删除”按钮的事件监听类******
	public class StuDeleteListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			// 获得被选中行的索引
			int selectedRow = adminStuTable.getSelectionModel().getSelectedIndex();
			// 判断是否存在被选中行
			if (selectedRow != -1) {
					connect();
				  try {
					String adminstu_delete = "delete from student where stu_id='" + chStuId.getText() + "';";
					String chStu=chStuId.getText();
					sta = con.createStatement();
					//删除学生信息记录
					boolrs = sta.execute(adminstu_delete);
					showAdminStudentList();
					//同时清空输入框
					chStuId.setText("");
					chStuName.setText("");
					chStuGender.setText("");
					chStuMajor.setText("");
					chStuClassroom.setText("");
					chStuTelephone.setText("");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("您已删除学号为" + chStu + "的学生信息记录！");
					alert.showAndWait();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}// 判断结束
	}
}
	public class InstrDeleteListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			// 获得被选中行的索引
			int selectedRow = adminInstrTable.getSelectionModel().getSelectedIndex();
			// 判断是否存在被选中行
			if (selectedRow != -1) {
					connect();
				  try {
					String admininstr_delete = "delete from instructor where instr_id='" + chInstrId.getText() + "';";
					String chInstr=chInstrId.getText();
					sta = con.createStatement();
					//删除教师信息记录
					boolrs = sta.execute(admininstr_delete);
					showAdminInstructorList();
					//同时清空输入框
					chInstrId.setText("");
					chInstrName.setText("");
					chInstrGender.setText("");
					chInstrJobTitle.setText("");
					chInstrStudy.setText("");
					chInstrTelephone.setText("");
					chInstrStudentNum.setText("");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("您已删除工号为" + chInstr + "的教师信息记录！");
					alert.showAndWait();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}// 判断结束
	}
}
	// ******点击“更新”按钮的事件监听类******
	public class StuUpdateListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			// 获得被选中行的索引
			int selectedRow = adminStuTable.getSelectionModel().getSelectedIndex();
			if (selectedRow != -1) {
				connect();
				try {
					Student abcObj = adminStuObsList.get(selectedRow);
	    	 		String adminstu_update = "update student set stu_id='" + chStuId.getText() + "',stu_name='" + chStuName.getText() + "',gender='" + chStuGender.getText() + "',major='" + chStuMajor.getText() + "',classroom='" + chStuClassroom.getText() + "',telephone='" + chStuTelephone.getText() + "' where stu_id='"+abcObj.getStuId()+"';";
	    			String chStu=chStuId.getText();
	    	 		sta = con.createStatement();
	    	 		//更新学生信息记录
	    			boolrs = sta.execute(adminstu_update);
	    			showAdminStudentList();
	    			//同时清空输入框
	    			chStuId.setText("");
	    			chStuName.setText("");
	    			chStuGender.setText("");
	    			chStuMajor.setText("");
	    			chStuClassroom.setText("");
	    			chStuTelephone.setText("");
	    			Alert alert = new Alert(AlertType.INFORMATION);
	    			alert.setTitle("Information Dialog");
	    			alert.setHeaderText(null);
	    			alert.setContentText("您已更新学号为" + chStu + "的学生信息记录！");
	    			alert.showAndWait();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
		}
		}
	public class InstrUpdateListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			// 获得被选中行的索引
			int selectedRow = adminInstrTable.getSelectionModel().getSelectedIndex();
			if (selectedRow != -1) {
				connect();
				try {
					Instructor abcObj = adminInstrObsList.get(selectedRow);
	    	 		String admininstr_update = "update instructor set instr_id='" + chInstrId.getText() + "',instr_name='" + chInstrName.getText() + "',gender='" + chInstrGender.getText() + "',job_title='" + chInstrJobTitle.getText() + "',study='" + chInstrStudy.getText() + "',telephone='" + chInstrTelephone.getText() + "',student_num='"+chInstrStudentNum.getText()+"' where instr_id='"+abcObj.getInstrId()+"';";
	    	 		String chInstr=chInstrId.getText();
	    	 		sta = con.createStatement();
	    	 		//更新教师信息
	    			boolrs = sta.execute(admininstr_update);
	    			showAdminInstructorList();
	    			//同时清空输入框
	    			chInstrId.setText("");
	    			chInstrName.setText("");
	    			chInstrGender.setText("");
	    			chInstrJobTitle.setText("");
	    			chInstrStudy.setText("");
	    			chInstrTelephone.setText("");
	    			chInstrStudentNum.setText("");
	    			Alert alert = new Alert(AlertType.INFORMATION);
	    			alert.setTitle("Information Dialog");
	    			alert.setHeaderText(null);
	    			alert.setContentText("您已更新工号为" + chInstr + "的教师信息记录！");
	    			alert.showAndWait();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
		}
		}

	public class Stu_numListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
				connect();
				connect();
				try {
	    	 		String admininstr_update = "update instructor set student_num='" + chInstrStudentNum.getText() + "';";
	    	 		String stuNum=chInstrStudentNum.getText();
	    			sta = con.createStatement();
	    			//统一更新学生人数
	    			boolrs = sta.execute(admininstr_update);
	    			showAdminInstructorList();
	    			//同时清空输入框
	    			chInstrId.setText("");
	    			chInstrName.setText("");
	    			chInstrGender.setText("");
	    			chInstrJobTitle.setText("");
	    			chInstrStudy.setText("");
	    			chInstrTelephone.setText("");
	    			chInstrStudentNum.setText("");
	    			Alert alert = new Alert(AlertType.INFORMATION);
	    			alert.setTitle("Information Dialog");
	    			alert.setHeaderText(null);
	    			alert.setContentText("您已统一修改学生人数为" + stuNum + "人！");
	    			alert.showAndWait();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	
	//*******类的定义*******************************************************************************************
	//定义教师类
	public class Instructor {
		private String instrId;
		private String instrName;
		private String gender;
		private String jobTitle;
		private String study;
		private String telephone;
		private String studentNum;
		private String configStuName;
		private String isFull;
		public Instructor(String instrId, String instrName, String gender, String jobTitle, 
				String study, String telephone, String studentNum) {
			this.instrId = instrId;
			this.instrName = instrName;
			this.gender = gender;
			this.jobTitle = jobTitle;
			this.study = study;
			this.telephone = telephone;
			this.studentNum = studentNum;
		}
		public Instructor( String instrId, String instrName, String gender, String jobTitle, 
				String study, String telephone, String studentNum, String configStuName, String isFull) {
			this.instrId = instrId;
			this.instrName = instrName;
			this.gender = gender;
			this.jobTitle = jobTitle;
			this.study = study;
			this.telephone = telephone;
			this.studentNum = studentNum;
			this.configStuName = configStuName;
			this.isFull = isFull;
		}
		public String getInstrId() {
			return instrId;
		}
		public void setInstrId(String instrId) {
			this.instrId = instrId;
		}
		public String getInstrName() {
			return instrName;
		}
		public void setInstrName(String instrName) {
			this.instrName = instrName;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public String getJobTitle() {
			return jobTitle;
		}
		public void setJobTitle(String jobTitle) {
			this.jobTitle = jobTitle;
		}
		public String getStudy() {
			return study;
		}
		public void setStudy(String study) {
			this.study = study;
		}
		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public String getStudentNum() {
			return studentNum;
		}
		public void setStudentNum(String studentNum) {
			this.studentNum = studentNum;
		}
	    
		public String getConfigStuName() {
			return configStuName;
		}
		public void setConfigStuName(String configStuName) {
			this.configStuName = configStuName;
		}
		public String getIsFull() {
			return isFull;
		}
		public void setIsFull(String isFull) {
			this.isFull = isFull;
		}


	}
	//定义教师类
	public class Student{
		private String stuId;
		private String stuName;
		private String stuGender;
		private String major;
		private String classroom;
		private String stuTelephone;
		private String state;
		private String instructor;
		public Student(String stuId,String stuName,String stuGender,
				String major,String classroom,String stuTelephone,
				String mystate,String instructor) {
			this.stuId = stuId;
			this.stuName = stuName;
			this.stuGender = stuGender;
			this.major = major;
			this.classroom = classroom;
			this.stuTelephone = stuTelephone;
			this.state = mystate;
			this.instructor = instructor;
		}
		public Student(String stuId,String stuName,String stuGender,
				String major,String classroom,String stuTelephone) {
			this.stuId = stuId;
			this.stuName = stuName;
			this.stuGender = stuGender;
			this.major = major;
			this.classroom = classroom;
			this.stuTelephone = stuTelephone;
			
		}

		public String getStuId() {
			return stuId;
		}

		public void setStuId(String stuId) {
			this.stuId = stuId;
		}

		public String getStuName() {
			return stuName;
		}

		public void setStuName(String stuName) {
			this.stuName = stuName;
		}

		public String getStuGender() {
			return stuGender;
		}

		public void setStuGender(String stuGender) {
			this.stuGender = stuGender;
		}

		public String getMajor() {
			return major;
		}

		public void setMajor(String major) {
			this.major = major;
		}

		public String getClassroom() {
			return classroom;
		}

		public void setClassroom(String classroom) {
			this.classroom = classroom;
		}

		public String getStuTelephone() {
			return stuTelephone;
		}

		public void setStuTelephone(String stuTelephone) {
			this.stuTelephone = stuTelephone;
		}
		public String getState() {
			return state;
		}
		public void setState(String mystate) {
			this.state=state;
		}
		public String getInstructor() {
			return instructor;
		}

		public void setInstructor(String instructor) {
			this.instructor = instructor;
		}
		

	}
	public static void main(String[] args) {
		launch(args);
	}

}