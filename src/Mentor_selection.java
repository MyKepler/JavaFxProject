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
	//*******ȫ�ֶ���*******************************************************************************************
	//*******������ѯ�����µ����Ķ���ͷ��ؽ��*******
	private Connection con;
	private Statement sta;
	private ResultSet rs; 
	private ResultSet instrRs; 
	private ResultSet stuRs; 
	private int updatestuRs;
	private int updatestuRs2;
	private Boolean boolrs;
	
	//*******������������,��ȡ���Ӷ����������ݿ�*******
	private String driverName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/mentor_selection";//ָ���������ݿ��URL
	private String User = "root"; //ָ���������ݿ���û���
	private String Password = "123456";//ָ���������ݿ������
	
	//*******��¼ҳ��*******
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
	
	//*******�޸�����ҳ��*******
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
	
	//*******�û���Ϣ*******
	private String userName;
	private int userState;
	private int userType;
	
	//*******ѧ����¼�ɹ�����*******
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
	
	//*******��ʦ��¼�ɹ�����*******
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
	
	//*******����Ա��¼�ɹ�����*******
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
	
	//*******����Ա��ѧ����*******
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
	
	//*******����Ա�Ľ�ʦ��*******
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
	
	private String[] choose = {"ȫ��","δѡ","��ѡ","ѡ��"};
	private TableColumn<Student, String> adminstuId;
	private TableColumn<Student, String> adminstuName;
	private TableColumn<Student, String> adminstuGender;
	private TableColumn<Student, String> adminmajor;
	private TableColumn<Student, String> adminclassroom;
	private TableColumn<Student, String> adminstuTelephone;
	private TableColumn<Student, String> adminstuState;
	private TableColumn<Student, String> adminstuInstrid;
	
	//*******��ʼ��̨����*******************************************************************************************
	@Override 
	public void start(Stage loginStage) {
		//*******��¼����*******************************************************************************************
		loginPane = new BorderPane();
		//��¼���涥��
		welLab = new Label("��ӭ��¼��ʦ�ƽ�Թ���ϵͳ");
		welcomeHB = new HBox(welLab);
		welcomeHB.setAlignment(Pos.CENTER);
		welcomeHB.setPadding(new Insets(20,0,20,0));
		loginPane.setTop(welcomeHB);
		
		//�û�����������������
		nameLab = new Label("�û���");
		nameLab.setPrefWidth(50);
		useridTF = new TextField();
		nameHB = new HBox(15,nameLab,useridTF);
		nameHB.setAlignment(Pos.CENTER);
		passwordLab = new Label("����  ");
		passwordLab.setPrefWidth(50);
		passwordTF = new PasswordField();
		passwordHB = new HBox(15,passwordLab,passwordTF);
		passwordHB.setAlignment(Pos.CENTER);
		infoVB = new VBox(15,nameHB,passwordHB);
		loginPane.setCenter(infoVB);
		
		//ѡ��ť������ͬ�Ľ���
		loginBtn = new Button("��¼");
		loginBtn.setMinWidth(60);
		modifyBtn = new Button("�޸�����");
		modifyBtn.setMinWidth(60);
		btnHB = new HBox(15,loginBtn,modifyBtn);
		btnHB.setAlignment(Pos.CENTER);
		btnHB.setPadding(new Insets(20,0,20,0));
		loginPane.setBottom(btnHB);
		
		//������̨��С�ͱ���
		loginScene = new Scene(loginPane, 400, 200);
		loginStage.setTitle("��¼����");
		loginStage.setScene(loginScene);
		loginStage.show();
		
		//��¼����ĵ�¼��ť�¼�����
		loginBtn.setOnAction(e->{
			//�������ݿ�
			connect();
			//��¼
			String loginSql = "select * from user where user_id = '" 
								+ useridTF.getText() + "'and user_password = '" 
								+ passwordTF.getText() +"';";
			try {
				//�ҵ�ƥ����û����������ʾ��¼�ɹ�
				if(login(loginSql)) {
					loginStage.hide();
					//�����û���������ת����ͬ�Ľ��棬����ѧ���ˣ���ʦ�ˣ�����Ա��)
					if(userType == 1) {
						studentStage.show();
						studentName.setText(userName);
						String stateSql = "select * from student where stu_id = '" + userName + "';";
						studentState.setText(checkStudentState(stateSql));
						showInstructorList();
					}
					//������ʦ��
					else if(userType == 2) {
						instructorStage.show();
						instructorName.setText(userName);
						showStudentList();
					}
					//��������Ա��
					else if(userType == 3) {
						adminName.setText(userName);
						administratorStage.show();
					}	
					System.out.println("��¼�ɹ�");	
				}
				else {
					System.out.println("��¼ʧ��");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//�ر����ݿ�
			closeConnection();
		});
		
		//��¼������޸����밴ť�¼�����
		modifyBtn.setOnAction(e->{
			loginStage.hide();
			modityStage.show();
		});
		
		//*******�޸��������*******************************************************************************************
		modifyPwdPane = new BorderPane();
		//�޸�������涥��
		welPwdLab = new Label("��ӭ��¼�޸��������");
		welPwdHB = new HBox(welPwdLab);
		welPwdHB.setAlignment(Pos.CENTER);
		welPwdHB.setPadding(new Insets(20,0,20,0));
		modifyPwdPane.setTop(welPwdHB);

		//�û��������������������������
		namePwdLab = new Label("�û���");
		namePwdLab.setPrefWidth(50);
		modify_useridTF = new TextField();
		namePwdHB = new HBox(15,namePwdLab,modify_useridTF);
		namePwdHB.setAlignment(Pos.CENTER);
		passwordOldPwdLab = new Label("������");
		passwordOldPwdLab.setPrefWidth(50);
		oldPwdTF = new TextField();
		passwordOldPwdHB = new HBox(15,passwordOldPwdLab,oldPwdTF);
		passwordOldPwdHB.setAlignment(Pos.CENTER);
		passwordPwdLab = new Label("������");
		passwordPwdLab.setPrefWidth(50);
		newPwdTF = new TextField();
		passwordPwdHB = new HBox(15,passwordPwdLab,newPwdTF);
		passwordPwdHB.setAlignment(Pos.CENTER);
		centerPwdVB = new VBox(15,namePwdHB,passwordOldPwdHB,passwordPwdHB);
		modifyPwdPane.setCenter(centerPwdVB);
		
		//ѡ��ťʵ�ֲ�ͬ�Ĺ���
		confignBtn = new Button("ȷ���޸�");
		cancleBtn = new Button("�����޸�");
		bottomPwdHB = new HBox(15,confignBtn,cancleBtn);
		bottomPwdHB.setAlignment(Pos.CENTER);
		bottomPwdHB.setPadding(new Insets(20,0,20,0));
		modifyPwdPane.setBottom(bottomPwdHB);
		
		//������̨��С�ͱ���
		modifyPwdScene = new Scene(modifyPwdPane, 400, 250);
		modityStage = new Stage();
		modityStage.setTitle("�޸��������");
		modityStage.setScene(modifyPwdScene);
		modityStage.hide();
		
		//�޸���������ȷ�ϰ�ť�¼�����
		confignBtn.setOnAction(e->{
			//�������ݿ�
			connect();
			//�޸�����
			String oldSql = "select * from user where user_id = '" 
						+ modify_useridTF.getText() + "'and user_password = '" 
						+ oldPwdTF.getText() +"';";
			//�ҵ�ƥ����û����;����뼴���޸�
			try {
				if(modifyPwd(oldSql)) {
					System.out.println("�޸ĳɹ�");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("�����޸ĳɹ���");
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
			//�ر����ݿ�
			closeConnection();
		});
		
		//�޸Ľ����ȡ����ť�¼�����
		cancleBtn.setOnAction(e->{
			loginStage.show();
			modityStage.hide();
		});
		
		//*******ѧ����¼�ɹ�����*******************************************************************************************
		studentTable = new TableView<Instructor>();
		//��������
		stuNav = new BorderPane();
		//���ݹ��Ų�ѯ��ʦ��Ϣ
		searchInstr = new HBox(15);
		searchInstr.setAlignment(Pos.CENTER_LEFT);
		searchInstr.setPadding(new Insets(10,10,0,10));
		labelInstr = new Label("��ʦ����");
		searchInstrTF = new TextField();
		searchInstrBtn = new Button("��ѯ");
		canclesearchInstrBtn = new Button("ȡ����ѯ");
		searchInstr.getChildren().addAll(labelInstr,searchInstrTF,searchInstrBtn,canclesearchInstrBtn);
		stuNav.setLeft(searchInstr);
		//���ݹ��Ų�ѯ��ʦ��Ϣ��ť�¼�����
		searchInstrBtn.setOnAction(event -> {
			    //�������ݿ�
			    connect();
				try {
				String instrSql = "select * from instructor where instr_id='" + searchInstrTF.getText() + "';";
				String stuSql = "";
				//��ʦ�б������
				instructorObsList.clear();
				sta = con.createStatement();
				//���ز�ѯ���Ľ�ʦ
				instrRs = sta.executeQuery(instrSql);
                //�Բ�ѯ�������
				while(instrRs.next()){
					stuSql = "select * from student where instr_id = '" + instrRs.getString(1) + "';";
					sta = con.createStatement();
					//����ѡ���ʦ���������ѧ��
					stuRs = sta.executeQuery(stuSql);
					//�ý�ʦ��ѧ������ʼ��Ϊ0
					int count = 0;
					//�洢ѡ��ý�ʦ��ѧ��������
					StringBuffer studentList = new StringBuffer();
					while(stuRs.next()) {
						//����ѧ����ѯ��¼�ó����� ��ƴ������
						count++;
						studentList.append(stuRs.getString(2)+"  ");
					}
					String studentNumStr = instrRs.getInt(7) + "";
					String studentAlrNumStr = count + "";
					//��ѧ�������ͽ�ʦ����̵�ѧ�������Ƚ��ж��Ƿ�����
					String isFullStr = instrRs.getInt(7) <= count ? "����" : "δ����";
					Instructor instructor = new Instructor(instrRs.getString(1), instrRs.getString(2), 
							instrRs.getString(3), instrRs.getString(4), instrRs.getString(5), 
							instrRs.getString(6),studentNumStr, studentList.toString(),isFullStr);
                    //�ڽ�ʦ�б���Ӽ�¼
					instructorObsList.add(instructor);
				}
				//���б����ѧ����¼�ɹ������TableView
				studentTable.setItems(instructorObsList);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        });
		
		//ȡ����ѯ��ʦ��Ϣ��ť�¼�����
		canclesearchInstrBtn.setOnAction(event -> {
			 try {
				//�������ݿ�
				connect();
				//��ʾ���н�ʦ����Ϣ
				showInstructorList();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
		
		//ѧ���ĸ�����Ϣ
		exitStuBtn = new Button("�˳�");
		stuInfo = new HBox(15,studentName,studentState,exitStuBtn);
		stuInfo.setAlignment(Pos.CENTER_RIGHT);
		stuInfo.setPadding(new Insets(10,10,0,10));
		stuNav.setRight(stuInfo);
		
		//�����ж���
		instrId = new TableColumn<Instructor,String>("����");
		instrId.setMinWidth(100);
		instrName = new TableColumn<Instructor,String>("����");
		instrName.setMinWidth(100);
		gender = new TableColumn<Instructor,String>("�Ա�");
		gender.setMinWidth(100);
		jobTitle = new TableColumn<Instructor,String>("ְ��");
		jobTitle.setMinWidth(100);
		study = new TableColumn<Instructor,String>("�о�����");
		study.setMinWidth(100);
		telephone = new TableColumn<Instructor,String>("�绰");
		telephone.setMinWidth(100);
		studentNum = new TableColumn<Instructor,String>("ѧ������");
		studentNum.setMinWidth(100);
		configStuName = new TableColumn<Instructor,String>("ѧ������");
		configStuName.setMinWidth(200);
		isFull = new TableColumn<Instructor,String>("�Ƿ�����");
		isFull.setMinWidth(100);
		action = new TableColumn<Instructor,String>("����");
		action.setMinWidth(200);
        //ѡ��ʦ������ť��
		Callback<TableColumn<Instructor, String>, TableCell<Instructor, String>> cellFactoryStu
        = new Callback<TableColumn<Instructor, String>, TableCell<Instructor, String>>() {
			 public TableCell call(final TableColumn<Instructor, String> param) {
			     final TableCell<Instructor, String> cell = new TableCell<Instructor, String>() {
			    	 final Button btn = new Button("ѡ��");
		             @Override
		             public void updateItem(String item, boolean empty) {
		                 super.updateItem(item, empty);
		                 if (empty) {
		                     setGraphic(null);
		                     setText(null);
		                 } else {
		                	 //����Ѿ���ѡ���� ��ť���
		                	 if(userState == 3) {
	                    	 		btn.setDisable(true);
		                	    }
		                	 else {
		                		//ѡ��ʦ������ť�¼�����
		                	     btn.setOnAction(event -> {
		                	 	 Instructor instructor = getTableView().getItems().get(getIndex());
		                    	 	//ѧ��״̬δѡ
		                    	 	if(userState == 1) {
		                    	 		//�������ݿ�
		                    	 		connect();
		                    	 		try {
		                    	 		String stu_state_update = "update student set state_id=2 where stu_id= '" + userName + "';";
		                    	 		String stu_instr_id_update="update student set instr_id='" + instructor.getInstrId() + "' where stu_id= '" + userName + "';";
		                        		sta = con.createStatement();
		                        		//����ѧ����״̬Ϊ��ѡ ��������ѡ��ʦ�Ĺ���
		                    			updatestuRs = sta.executeUpdate(stu_state_update);
		                    			updatestuRs2=sta.executeUpdate(stu_instr_id_update);
		                    			showInstructorList();
		                    			studentState.setText("��ѡ");
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
		                    	 	}
		                    	 	//ѧ��״̬��ѡ
		                    	 	else if(userState == 2) {
		                    	 		//�������ݿ�
		                    	 		connect();
		                    	 		try {
		                    	 		String stu_instr_id_update="update student set instr_id='" + instructor.getInstrId() + "' where stu_id= '" + userName + "';";
		                    			sta = con.createStatement();
		                    			//����ѧ��ѡ��ʦ�Ĺ���
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
		
		//���ж�����ӵ�����ͼ
		studentTable.getColumns().addAll(instrId,instrName,gender,jobTitle,study,telephone,studentNum,configStuName,isFull,action);
		studentTable.setItems(instructorObsList);
		studentTable.setPrefHeight(400);
        
		//�������б���ж���������
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
        
		//������̨��С�ͱ���
		studentVB = new VBox(15,stuNav,studentTable);
		studentScene = new Scene(studentVB, 1200, 400);
		studentStage = new Stage();
		studentStage.setTitle("ѧ������");
		studentStage.setMaxWidth(1200);
		studentStage.setScene(studentScene);
		studentStage.hide();
		
		//*******��ʦ��¼�ɹ�����*******************************************************************************************		
		instructorTable = new TableView<Student>();
		//��������
		instrNav = new BorderPane();
		//����ѧ�Ų�ѯѧ����Ϣ
		searchStu = new HBox(15);
		searchStu.setAlignment(Pos.CENTER_LEFT);
		searchStu.setPadding(new Insets(10,10,0,10));
		labelStu = new Label("ѧ��ѧ��");
		searchStuTF = new TextField();
		searchStuBtn = new Button("��ѯ");
		canclesearchStuBtn = new Button("ȡ����ѯ");
		searchStu.getChildren().addAll(labelStu,searchStuTF,searchStuBtn,canclesearchStuBtn);
		instrNav.setLeft(searchStu);
		//����ѧ�Ų�ѯѧ����Ϣ��ť�¼�����
		searchStuBtn.setOnAction(event -> {
			//�������ݿ�
			connect();
    	 	String stuSearch = "select * from student where instr_id = '" + userName + "' and stu_id='"+searchStuTF.getText()+"';";
			try {
				sta = con.createStatement();
				//���ز�ѯ����ѧ��
			    stuRs = sta.executeQuery(stuSearch);
			    studentObsList.clear();
			    while(stuRs.next()){
					String state_show;
					//����״̬���� ת��Ϊ����״̬��ʾ
					if(stuRs.getInt(7)==1)state_show="δѡ";
					else if(stuRs.getInt(7)==2)state_show="��ѡ";
					else state_show="ѡ��";
					Student student = new Student(stuRs.getString(1),stuRs.getString(2),stuRs.getString(3),stuRs.getString(4),
							                      stuRs.getString(5),stuRs.getString(6),state_show,stuRs.getString(8));
					//��ѧ���б���Ӽ�¼
					studentObsList.add(student);
				  }
			  //���б�����ʦ��¼�ɹ������TableView
				instructorTable.setItems(studentObsList);	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}		 
        });
		
		//ȡ����ѯѧ����Ϣ��ť�¼�����
		canclesearchStuBtn.setOnAction(event -> {
			 try {
				connect();
				showStudentList();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
		
		//��ʦ�ĸ�����Ϣ
		instrInfo = new HBox(15);
		instrInfo.setAlignment(Pos.CENTER_RIGHT);
		instrInfo.setPadding(new Insets(10,10,0,10));
		exitInstrBtn = new Button("�˳�");
		instrInfo.getChildren().addAll(instructorName,instructorState,exitInstrBtn);
		instrNav.setRight(instrInfo);
		
		//�����ж���
		stuId = new TableColumn<Student,String>("ѧ��");
		stuId.setMinWidth(100);
		stuName = new TableColumn<Student,String>("����");
		stuName.setMinWidth(100);
		stuGender = new TableColumn<Student,String>("�Ա�");
		stuGender.setMinWidth(50);
		major = new TableColumn<Student,String>("רҵ");
		major.setMinWidth(200);
		classroom = new TableColumn<Student,String>("�༶");
		classroom.setMinWidth(100);
		stuTelephone = new TableColumn<Student,String>("�绰");
		stuTelephone.setMinWidth(100);
		state = new TableColumn<Student,String>("״̬");
		state.setMinWidth(50);
		instructor = new TableColumn<Student,String>("��ʦ");
		instructor.setMinWidth(100);
		stuAction = new TableColumn<Student,String>("����");
		stuAction.setMinWidth(200);
		//ѡѧ��������ť��
		Callback<TableColumn<Student, String>, TableCell<Student, String>> cellFactoryInstr
        = new Callback<TableColumn<Student, String>, TableCell<Student, String>>() {
			 public TableCell call(final TableColumn<Student, String> param) {
			     final TableCell<Student, String> cell = new TableCell<Student, String>() {
		             Button selectBtn = new Button("ѡ��");
		             Button obsolescenceBtn = new Button("��̭");
		             HBox instroperation = new HBox(15,selectBtn,obsolescenceBtn);
		             @Override
		             public void updateItem(String item, boolean empty) {
		                 super.updateItem(item, empty);
		                 if (empty) {
		                     setGraphic(null);
		                     setText(null);
		                 } else {
		                	    //�Ƚ���ť����ֹ����
		                	    selectBtn.setDisable(true);
		                	    obsolescenceBtn.setDisable(true);
		                	 	Student student = getTableView().getItems().get(getIndex());
             		    		if(student.getInstructor() != null) {
             		    			if(student.getState()!="ѡ��") {
             		    				//���ѧ��״̬����ѡ�� �ٻָ���ť
             		    				selectBtn.setDisable(false);
             		    				obsolescenceBtn.setDisable(false);
             			        	}
             		    		}	
             		    		//ѡ��ѧ��������ť�¼�����
             		    		selectBtn.setOnAction(event -> {
	                	    			    //�������ݿ�
	                	    			    connect();
			                    	 		try {
			                    	 		String stu_state_update = "update student set state_id=3 where stu_id= '" + student.getStuId() + "';";
			                    			sta = con.createStatement();
			                    			//���±�����ѧ����״̬Ϊѡ��
			                    			updatestuRs = sta.executeUpdate(stu_state_update);
			                    			showStudentList();
											} catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
		                     });
             		    		obsolescenceBtn.setOnAction(event -> {
	                	    			//�������ݿ�
	                	    			connect();
	                	    			try {
			                    	 		String stu_state_update = "update student set state_id=1 where stu_id= '" + student.getStuId() + "';";
			                    	 		String stu_instr_id_update="update student set instr_id=null where stu_id= '" + student.getStuId() + "';";
			                    			sta = con.createStatement();
			                    			//����ѧ��״̬Ϊδѡ�������ѡ��ʦ�Ĺ���
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
		
		//���ж�����ӵ�����ͼ
		instructorTable.getColumns().addAll(stuId,stuName,stuGender,major,classroom,stuTelephone,state,instructor,stuAction);
		instructorTable.setItems(studentObsList);
		instructorTable.setPrefHeight(400);
		
		//�������б���ж���������
		stuId.setCellValueFactory(new PropertyValueFactory<Student, String>("stuId"));
		stuName.setCellValueFactory(new PropertyValueFactory<Student, String>("stuName"));
		stuGender.setCellValueFactory(new PropertyValueFactory<Student, String>("stuGender"));
		major.setCellValueFactory(new PropertyValueFactory<Student, String>("major"));
		classroom.setCellValueFactory(new PropertyValueFactory<Student, String>("classroom"));
		stuTelephone.setCellValueFactory(new PropertyValueFactory<Student, String>("stuTelephone"));
		state.setCellValueFactory(new PropertyValueFactory<Student,String>("state"));
		instructor.setCellValueFactory(new PropertyValueFactory<Student, String>("instructor"));
		
		//������̨��С�ͱ���
		instructorVB = new VBox(15,instrNav,instructorTable);
		instructorScene = new Scene(instructorVB, 1050, 420);
		instructorStage = new Stage();
		instructorStage.setTitle("��ʦ����");
		instructorStage.setMaxWidth(1050);
		instructorStage.setScene(instructorScene);
		instructorStage.hide();
		
		//*******����Ա����*******************************************************************************************
		//��������
		adminNav = new BorderPane();
		//����Ա�ĸ�����Ϣ
		returnAdminBtn = new Button("�˳�");
		adminInfo = new HBox(10,adminName,adminState,returnAdminBtn);
		adminInfo.setAlignment(Pos.CENTER_RIGHT);
		adminInfo.setPadding(new Insets(10,10,0,10));
		adminNav.setRight(adminInfo);
		
		//*******��������ҳ��*******
		adminAct = new HBox(15);
		adminAct.setAlignment(Pos.CENTER);
		adminAct.setPadding(new Insets(40,10,0,10));
	    alterStuBtn = new Button("ѧ����Ϣ����");
		alterInstrBtn = new Button("��ʦ��Ϣ����");
		adminAct.getChildren().addAll(alterStuBtn,alterInstrBtn);
		administratorVB = new VBox(15,adminNav,adminAct);
		administratorScene = new Scene(administratorVB, 400, 200);
		administratorStage = new Stage();
		administratorStage.setTitle("����Ա����");
		administratorStage.setMaxWidth(400);
		administratorStage.setScene(administratorScene);
		administratorStage.hide();		
		//����Աѡ���������Ϊѧ���İ�ť�¼�����
		alterStuBtn.setOnAction(e->{
			adminStuStage.show();
			try {
				showAdminStudentList();
			} catch (SQLException e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		//����Աѡ���������Ϊ��ʦ�İ�ť�¼�����
		alterInstrBtn.setOnAction(e->{
			adminInstrStage.show();
			try {
				showAdminInstructorList();
			} catch (SQLException e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
						
		//*******�鿴��ʦ�б�*******	
		adminInstrTable = new TableView<Instructor>();
		adminInstrTable.setPrefHeight(400);
		adminInstrTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		//������ͼע������¼�
		adminInstrTable.setOnMouseClicked(new InstrMouseClickedListener());
		//�����ж���
		admininstrId = new TableColumn<Instructor,String>("����");
		admininstrId.setMinWidth(100);
		admininstrName = new TableColumn<Instructor,String>("����");
		admininstrName.setMinWidth(100);
		admingender = new TableColumn<Instructor,String>("�Ա�");
		admingender.setMinWidth(100);
		adminjobTitle = new TableColumn<Instructor,String>("ְ��");
		adminjobTitle.setMinWidth(100);
		adminstudy = new TableColumn<Instructor,String>("�о�����");
		adminstudy.setMinWidth(100);
		admintelephone = new TableColumn<Instructor,String>("�绰");
		admintelephone.setMinWidth(100);
		adminstudentNum = new TableColumn<Instructor,String>("ѧ������");
		adminstudentNum.setMinWidth(100);
		//���ж�����ӵ�����ͼ
		adminInstrTable.getColumns().addAll(admininstrId,admininstrName,admingender,adminjobTitle,adminstudy,admintelephone,adminstudentNum);
		//�������б���ж���������
		admininstrId.setCellValueFactory(new PropertyValueFactory<Instructor, String>("instrId"));
		admininstrName.setCellValueFactory(new PropertyValueFactory<Instructor, String>("instrName"));
		admingender.setCellValueFactory(new PropertyValueFactory<Instructor, String>("gender"));
		adminjobTitle.setCellValueFactory(new PropertyValueFactory<Instructor, String>("jobTitle"));
		adminstudy.setCellValueFactory(new PropertyValueFactory<Instructor, String>("study"));
		admintelephone.setCellValueFactory(new PropertyValueFactory<Instructor, String>("telephone"));
		adminstudentNum.setCellValueFactory(new PropertyValueFactory<Instructor, String>("studentNum"));
		
		//*******����Ա�鿴�༭��ʦ��Ϣ*******
		Label InstrIdlabel=new Label("���ţ�");
		Label InstrNamelabel=new Label("����:         ");
		Label InstrGenderlabel=new Label("�Ա�");
		Label InstrJobTitlelabel=new Label("ְ�ƣ�");
		Label InstrStudylabel=new Label("�о�����");
		Label InstrTelephonelabel=new Label("�绰��");
		Label InstrStudentNumlabel=new Label("ѧ��������");
		
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
		
		addInstr = new Button("���");
		addInstr.setPrefWidth(50);
		addInstr.setOnAction(new InstrAddListener());
		deleteInstr = new Button("ɾ��");
		deleteInstr.setPrefWidth(50);
		deleteInstr.setOnAction(new InstrDeleteListener());
		updateInstr = new Button("�޸�");
		updateInstr.setPrefWidth(50);
		updateInstr.setOnAction(new InstrUpdateListener());
		setstu_num= new Button("�޸�");
		setstu_num.setPrefWidth(50);
		setstu_num.setOnAction(new Stu_numListener());
		
		//����������ʾѡ���е���Ϣ ��ť�ɽ�����Ӧ����
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
		
		//������̨��С�ͱ���
		adminInstrScene = new Scene(adminInstr, 800, 420);
		adminInstrStage = new Stage();
		adminInstrStage.setTitle("����Ա�ˡ�����ʦ��Ϣ");
		adminInstrStage.setMaxWidth(800);
		adminInstrStage.setScene(adminInstrScene);
		adminInstrStage.hide();
		
		//*******�鿴ѧ���б�*******
		adminStuTable = new TableView<Student>();
		adminStuTable.setPrefHeight(400);
		adminStuTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		//������ͼע������¼�
		adminStuTable.setOnMouseClicked(new StuMouseClickedListener());
		//�����ж���			
		adminstuId = new TableColumn<Student,String>("ѧ��");
		adminstuId.setMinWidth(100);
		adminstuName = new TableColumn<Student,String>("����");
		adminstuName.setMinWidth(100);
		adminstuGender = new TableColumn<Student,String>("�Ա�");
		adminstuGender.setMinWidth(50);
		adminmajor = new TableColumn<Student,String>("רҵ");
		adminmajor.setMinWidth(200);
		adminclassroom = new TableColumn<Student,String>("�༶");
		adminclassroom.setMinWidth(100);
		adminstuTelephone = new TableColumn<Student,String>("�绰");
		adminstuTelephone.setMinWidth(100);
		adminstuState = new TableColumn<Student,String>("״̬");
		adminstuState.setMinWidth(100);
		adminstuInstrid = new TableColumn<Student,String>("��ʦ");
		adminstuInstrid.setMinWidth(100);
		//���ж�����ӵ�����ͼ
		adminStuTable.getColumns().addAll(adminstuId,adminstuName,adminstuGender,adminmajor,adminclassroom,adminstuTelephone,adminstuState,adminstuInstrid);
		//�������б���ж���������
		adminstuId.setCellValueFactory(new PropertyValueFactory<Student, String>("stuId"));
		adminstuName.setCellValueFactory(new PropertyValueFactory<Student, String>("stuName"));
		adminstuGender.setCellValueFactory(new PropertyValueFactory<Student, String>("stuGender"));
		adminmajor.setCellValueFactory(new PropertyValueFactory<Student, String>("major"));
		adminclassroom.setCellValueFactory(new PropertyValueFactory<Student, String>("classroom"));
		adminstuTelephone.setCellValueFactory(new PropertyValueFactory<Student, String>("stuTelephone"));
		adminstuState.setCellValueFactory(new PropertyValueFactory<Student, String>("state"));
		adminstuInstrid.setCellValueFactory(new PropertyValueFactory<Student, String>("instructor"));
		
		//*******����Ա�鿴�༭ѧ����Ϣ*******
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
		Label Stuidlabel=new Label("ѧ��:");
		Label StuNamelabel=new Label("����:");
		Label StuGenderlabel=new Label("�Ա�:");
		Label StuMajorlabel=new Label("רҵ:");
		Label StuClassroomlabel=new Label("�༶:");
        Label StuTelephonelabel=new Label("�绰:");
        Label StuStatelabel=new Label("ѧ��״̬");
		addStu = new Button("���");
		addStu.setPrefWidth(50);
		addStu.setOnAction(new StuAddListener());
		deleteStu = new Button("ɾ��");
		deleteStu.setPrefWidth(50);
		deleteStu.setOnAction(new StuDeleteListener());
		updateStu = new Button("�޸�");
		updateStu.setPrefWidth(50);
		updateStu.setOnAction(new StuUpdateListener());
		combobox = new ComboBox<>();
		combobox.setPrefWidth(100);
		combobox.setValue("ȫ��");
		ObservableList<String> items = FXCollections.observableArrayList(choose);
		combobox.setItems(items);
		
		//*******ͨ��ѧ��״̬ɸѡѧ��*******
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
				    //�������ݿ�
					connect();
					String stuSql = "select * from student where state_id=1;";
					try {
						sta = con.createStatement();
						//ɸѡ��ѧ��״̬Ϊδѡ��ѧ��
					    stuRs = sta.executeQuery(stuSql);
					    //���ѧ���б�
					    adminStuObsList.clear();
					    while(stuRs.next()){
						String state_show;
						if(stuRs.getInt(7)==1)state_show="δѡ";
						else if(stuRs.getInt(7)==2)state_show="��ѡ";
						else state_show="ѡ��";
						Student student2 = new Student(stuRs.getString(1),stuRs.getString(2),stuRs.getString(3),stuRs.getString(4),stuRs.getString(5),stuRs.getString(6),state_show,stuRs.getString(8));
						adminStuObsList.add(student2);
					}
					//���б�������Ա�༭ѧ����Ϣ�����TableView
					adminStuTable.setItems(adminStuObsList);
					closeConnection();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
			else if(items.indexOf(combobox.getValue()) == 2) {
				//�������ݿ�
				connect();
				String stuSql = "select * from student where state_id=2;";
				try {
					sta = con.createStatement();
					//ɸѡ��ѧ��״̬Ϊ��ѡ��ѧ��
				    stuRs = sta.executeQuery(stuSql);
				    adminStuObsList.clear();
				    while(stuRs.next()){
					String state_show;
					if(stuRs.getInt(7)==1)state_show="δѡ";
					else if(stuRs.getInt(7)==2)state_show="��ѡ";
					else state_show="ѡ��";
					Student student2 = new Student(stuRs.getString(1),stuRs.getString(2),stuRs.getString(3),stuRs.getString(4),stuRs.getString(5),stuRs.getString(6),state_show,stuRs.getString(8));
					adminStuObsList.add(student2);
				}
				//���б�������Ա�༭ѧ����Ϣ�����TableView
				adminStuTable.setItems(adminStuObsList);
				closeConnection();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				//�������ݿ�
				connect();
				String stuSql = "select * from student where state_id=3;";
				try {
					sta = con.createStatement();
					//ɸѡ��ѧ��״̬Ϊ��ѡ��ѧ��
				    stuRs = sta.executeQuery(stuSql);
				    adminStuObsList.clear();
				    while(stuRs.next()){
					String state_show;
					if(stuRs.getInt(7)==1)state_show="δѡ";
					else if(stuRs.getInt(7)==2)state_show="��ѡ";
					else state_show="ѡ��";
					Student student2 = new Student(stuRs.getString(1),stuRs.getString(2),stuRs.getString(3),stuRs.getString(4),stuRs.getString(5),stuRs.getString(6),state_show,stuRs.getString(8));
					adminStuObsList.add(student2);
				}
				//���б�������Ա�༭ѧ����Ϣ�����TableView
				adminStuTable.setItems(adminStuObsList);
				closeConnection();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		//����������ʾѡ���е���Ϣ ��ť�ɽ�����Ӧ����
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
		
		//������̨��С�ͱ���
		adminStuScene = new Scene(adminStu, 1000, 420);
		adminStuStage = new Stage();
		adminStuStage.setTitle("����Ա�ˡ���ѧ����Ϣ");
		adminStuStage.setMaxWidth(1000);
		adminStuStage.setScene(adminStuScene);
		adminStuStage.hide();
		
		//*******�˳���ť*******
		//ѧ���˳���ť�¼�����
		exitStuBtn.setOnAction(e->{
			useridTF.setText("");
			passwordTF.setText("");
			studentStage.hide();
			loginStage.show();
		});
		
		//��ʦ�˳���ť�¼�����
		exitInstrBtn.setOnAction(e->{
			useridTF.setText("");
			passwordTF.setText("");
			instructorStage.hide();
			loginStage.show();
		});
		
		//����Ա�˳���ť�¼�����
		returnAdminBtn.setOnAction(e->{
			useridTF.setText("");
			passwordTF.setText("");
			administratorStage.hide();
			loginStage.show();
		});		
   }
	
	//*******��������*******************************************************************************************
	//�������ݿ�
	public void connect() {
		try {
			Class.forName(driverName);
			// �������ݿ�����
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
	//�ر����ݿ�
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
	//�ж��Ƿ�ɹ���¼
    public boolean login(String sql) throws SQLException {
		try {
			sta = con.createStatement();
			//���ز�ѯ��ѧ������ʦ�����Ա
			rs = sta.executeQuery(sql.toString());
			if(rs.next()) {
				//��¼���ͺ��û���
				userType = rs.getInt(3);
				userName = rs.getString(1);
				return true;
			}
			//��ѯʧ���򵯳����ѿ�
			else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("��������û��������벻��ȷ��");
				alert.showAndWait();
				passwordTF.setText("");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("connect fail" + e.getMessage());
			return false;
		}	
	}
   //�޸�����
	public boolean modifyPwd(String oldSql) throws SQLException{
		//�����ѯ�ɹ��͸�������
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
	//��ʾ��ʦ�б�
	public void showInstructorList() throws SQLException {	
		String instrSql = "select * from instructor;";
		String stuSql = "";
		instructorObsList.clear();
		sta = con.createStatement();
		//���ز�ѯ���Ľ�ʦ
		instrRs = sta.executeQuery(instrSql);
		//�Բ�ѯ�������
		while(instrRs.next()){
			stuSql = "select * from student where instr_id = '" + instrRs.getString(1) + "';";
			sta = con.createStatement();
			stuRs = sta.executeQuery(stuSql);
			//�ý�ʦ��ѧ������ʼ��Ϊ0
			int count = 0;
			//�洢ѡ��ý�ʦ��ѧ��������
			StringBuffer studentList = new StringBuffer();
			while(stuRs.next()) {
				//����ѧ����ѯ��¼�ó����� ��ƴ������
				count++;
				studentList.append(stuRs.getString(2)+"  ");
			}
			String studentNumStr = instrRs.getInt(7) + "";
			String studentAlrNumStr = count + "";
			//��ѧ�������ͽ�ʦ����̵�ѧ�������Ƚ��ж��Ƿ�����
			String isFullStr = instrRs.getInt(7) <= count ? "����" : "δ����";
			Instructor instructor = new Instructor(instrRs.getString(1), instrRs.getString(2), 
					instrRs.getString(3), instrRs.getString(4), instrRs.getString(5), 
					instrRs.getString(6),studentNumStr, studentList.toString(),isFullStr);
			//�ڽ�ʦ�б���Ӽ�¼
			instructorObsList.add(instructor);
		}
		//���б����TableView
		studentTable.setItems(instructorObsList);
	}
	//����Ա����ʾ��ʦ�б�
	public void showAdminInstructorList() throws SQLException {	
		connect();
		String instrSql = "select * from instructor;";
		String stuSql = "";
		adminInstrObsList.clear();
		sta = con.createStatement();
		//���ز�ѯ���Ľ�ʦ
		instrRs = sta.executeQuery(instrSql);
		while(instrRs.next()){
			stuSql = "select * from student where instr_id = '" + instrRs.getString(1) + "';";
			sta = con.createStatement();
			stuRs = sta.executeQuery(stuSql);
			//�ý�ʦ��ѧ������ʼ��Ϊ0
			int count = 0;
			while(stuRs.next()) {
				//����ѧ����ѯ��¼�ó�����
				count++;
			}
			String studentNumStr = instrRs.getInt(7)+"";
			String studentAlrNumStr = count + "";
			Instructor instructor1 = new Instructor(instrRs.getString(1), instrRs.getString(2),
			instrRs.getString(3), instrRs.getString(4), instrRs.getString(5),instrRs.getString(6),studentNumStr);
			//�ڹ���Ա����Ľ�ʦ�б���Ӽ�¼
			adminInstrObsList.add(instructor1);
		}
		//���б����TableView
		adminInstrTable.setItems(adminInstrObsList);
		closeConnection();
	}
	//��ʾѧ���б�
	public void showStudentList() throws SQLException {
		String stuSql = "select * from student where instr_id = '" + userName + "';";
		sta = con.createStatement();
		//���ز�ѯ����ѧ��
		stuRs = sta.executeQuery(stuSql);
		studentObsList.clear();
		while(stuRs.next()){
			//����״̬���� ת��Ϊ����״̬��ʾ
			String state_show;
			if(stuRs.getInt(7)==1)state_show="δѡ";
			else if(stuRs.getInt(7)==2)state_show="��ѡ";
			else state_show="ѡ��";
			Student student = new Student(stuRs.getString(1),stuRs.getString(2),stuRs.getString(3),stuRs.getString(4),
					stuRs.getString(5),stuRs.getString(6),state_show,stuRs.getString(8));
			//��ѧ���б���Ӽ�¼
			studentObsList.add(student);
		}
		//���б����TableView
		instructorTable.setItems(studentObsList);
	}
	//��ʾ����Ա�˵�ѧ���б�
	public void showAdminStudentList() throws SQLException {
		connect();
		String stuSql = "select * from student;";
		sta = con.createStatement();
		//���ز�ѯ����ѧ��
		stuRs = sta.executeQuery(stuSql);
		adminStuObsList.clear();
		while(stuRs.next()){
			//����״̬���� ת��Ϊ����״̬��ʾ
			String state_show;
			if(stuRs.getInt(7)==1)state_show="δѡ";
			else if(stuRs.getInt(7)==2)state_show="��ѡ";
			else state_show="ѡ��";
			Student student2 = new Student(stuRs.getString(1),stuRs.getString(2),stuRs.getString(3),stuRs.getString(4),stuRs.getString(5),stuRs.getString(6),state_show,stuRs.getString(8));
			//��ѧ���б���Ӽ�¼
			adminStuObsList.add(student2);
		}
		//���б����TableView
		adminStuTable.setItems(adminStuObsList);
		closeConnection();
	}
	//�������ݿ�ѧ��״̬������ת��Ϊ������ʾ
	public String checkStudentState(String stateSql) throws SQLException {
		String stateStr = "";
		sta = con.createStatement();
		stuRs = sta.executeQuery(stateSql);
		while(stuRs.next()) {
			userState = stuRs.getInt(7);
			if(stuRs.getInt(7) == 1) {
				stateStr = "δѡ";
				
			}
			else if(stuRs.getInt(7) == 2) {
				stateStr = "��ѡ";
			}
			else if(stuRs.getInt(7) == 3) {
				stateStr = "ѡ��";
			}
		}
		return stateStr;
	}
	
	//*******�¼���������Ӧ��*******************************************************************************************
	public class StuMouseClickedListener implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent arg0) {
			// �õ��û�ѡ��ļ�¼
			int selectedRow = adminStuTable.getSelectionModel().getSelectedIndex();
			// ���ȷʵѡȡ��ĳ����¼
			if(selectedRow!=-1){
				// ��ȡѡ��ļ�¼
				Student abcObj = adminStuObsList.get(selectedRow);
				// ���û�ѡ��ļ�¼�е����ݷֱ���ӵ���Ӧ���ı�����
				chStuId.setText(abcObj.getStuId());
				chStuName.setText(abcObj.getStuName());
				chStuGender.setText(abcObj.getStuGender());
				chStuMajor.setText(abcObj.getMajor());
				chStuClassroom.setText(abcObj.getClassroom());
				chStuTelephone.setText(abcObj.getStuTelephone());
			// if������
			}
		}
	}
	public class InstrMouseClickedListener implements EventHandler<MouseEvent> {
			@Override
			public void handle(MouseEvent arg0) {
				// �õ��û�ѡ��ļ�¼
				int selectedRow = adminInstrTable.getSelectionModel().getSelectedIndex();
				// ���ȷʵѡȡ��ĳ����¼
				if(selectedRow!=-1){
					// ��ȡѡ��ļ�¼
					Instructor abcObj = adminInstrObsList.get(selectedRow);
					// ���û�ѡ��ļ�¼�е����ݷֱ���ӵ���Ӧ���ı�����
					chInstrId.setText(abcObj.getInstrId());
					chInstrName.setText(abcObj.getInstrName());
					chInstrGender.setText(abcObj.getGender());
					chInstrJobTitle.setText(abcObj.getJobTitle());
					chInstrStudy.setText(abcObj.getStudy());
					chInstrTelephone.setText(abcObj.getTelephone());
					chInstrStudentNum.setText(abcObj.getStudentNum());
				// if������
				}
			}
		}

	//******�������ӡ���ť���¼�������******
	public class StuAddListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			connect();
			try {
    	 		String adminstu_add = "insert into student values('" + chStuId.getText() + "','" + chStuName.getText() + "','" + chStuGender.getText() + "','" + chStuMajor.getText() + "','" + chStuClassroom.getText() + "','" + chStuTelephone.getText() + "',1,null);";
    			String chStu=chStuId.getText();
    	 		sta = con.createStatement();
    	 		//���ѧ����Ϣ��¼
    			boolrs = sta.execute(adminstu_add);
    			//ͬʱ��������
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
    			alert.setContentText("��������һ��ѧ��Ϊ" + chStu + "��ѧ����Ϣ��¼��");
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
    	 		//��ӽ�ʦ��Ϣ��¼
    			boolrs = sta.execute(admininstr_add);
    			showAdminInstructorList();
    			//ͬʱ��������
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
    			alert.setContentText("��������һ������Ϊ" + chInstr + "�Ľ�ʦ��Ϣ��¼��");
    			alert.showAndWait();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	// ******�����ɾ������ť���¼�������******
	public class StuDeleteListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			// ��ñ�ѡ���е�����
			int selectedRow = adminStuTable.getSelectionModel().getSelectedIndex();
			// �ж��Ƿ���ڱ�ѡ����
			if (selectedRow != -1) {
					connect();
				  try {
					String adminstu_delete = "delete from student where stu_id='" + chStuId.getText() + "';";
					String chStu=chStuId.getText();
					sta = con.createStatement();
					//ɾ��ѧ����Ϣ��¼
					boolrs = sta.execute(adminstu_delete);
					showAdminStudentList();
					//ͬʱ��������
					chStuId.setText("");
					chStuName.setText("");
					chStuGender.setText("");
					chStuMajor.setText("");
					chStuClassroom.setText("");
					chStuTelephone.setText("");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("����ɾ��ѧ��Ϊ" + chStu + "��ѧ����Ϣ��¼��");
					alert.showAndWait();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}// �жϽ���
	}
}
	public class InstrDeleteListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			// ��ñ�ѡ���е�����
			int selectedRow = adminInstrTable.getSelectionModel().getSelectedIndex();
			// �ж��Ƿ���ڱ�ѡ����
			if (selectedRow != -1) {
					connect();
				  try {
					String admininstr_delete = "delete from instructor where instr_id='" + chInstrId.getText() + "';";
					String chInstr=chInstrId.getText();
					sta = con.createStatement();
					//ɾ����ʦ��Ϣ��¼
					boolrs = sta.execute(admininstr_delete);
					showAdminInstructorList();
					//ͬʱ��������
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
					alert.setContentText("����ɾ������Ϊ" + chInstr + "�Ľ�ʦ��Ϣ��¼��");
					alert.showAndWait();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}// �жϽ���
	}
}
	// ******��������¡���ť���¼�������******
	public class StuUpdateListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			// ��ñ�ѡ���е�����
			int selectedRow = adminStuTable.getSelectionModel().getSelectedIndex();
			if (selectedRow != -1) {
				connect();
				try {
					Student abcObj = adminStuObsList.get(selectedRow);
	    	 		String adminstu_update = "update student set stu_id='" + chStuId.getText() + "',stu_name='" + chStuName.getText() + "',gender='" + chStuGender.getText() + "',major='" + chStuMajor.getText() + "',classroom='" + chStuClassroom.getText() + "',telephone='" + chStuTelephone.getText() + "' where stu_id='"+abcObj.getStuId()+"';";
	    			String chStu=chStuId.getText();
	    	 		sta = con.createStatement();
	    	 		//����ѧ����Ϣ��¼
	    			boolrs = sta.execute(adminstu_update);
	    			showAdminStudentList();
	    			//ͬʱ��������
	    			chStuId.setText("");
	    			chStuName.setText("");
	    			chStuGender.setText("");
	    			chStuMajor.setText("");
	    			chStuClassroom.setText("");
	    			chStuTelephone.setText("");
	    			Alert alert = new Alert(AlertType.INFORMATION);
	    			alert.setTitle("Information Dialog");
	    			alert.setHeaderText(null);
	    			alert.setContentText("���Ѹ���ѧ��Ϊ" + chStu + "��ѧ����Ϣ��¼��");
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
			// ��ñ�ѡ���е�����
			int selectedRow = adminInstrTable.getSelectionModel().getSelectedIndex();
			if (selectedRow != -1) {
				connect();
				try {
					Instructor abcObj = adminInstrObsList.get(selectedRow);
	    	 		String admininstr_update = "update instructor set instr_id='" + chInstrId.getText() + "',instr_name='" + chInstrName.getText() + "',gender='" + chInstrGender.getText() + "',job_title='" + chInstrJobTitle.getText() + "',study='" + chInstrStudy.getText() + "',telephone='" + chInstrTelephone.getText() + "',student_num='"+chInstrStudentNum.getText()+"' where instr_id='"+abcObj.getInstrId()+"';";
	    	 		String chInstr=chInstrId.getText();
	    	 		sta = con.createStatement();
	    	 		//���½�ʦ��Ϣ
	    			boolrs = sta.execute(admininstr_update);
	    			showAdminInstructorList();
	    			//ͬʱ��������
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
	    			alert.setContentText("���Ѹ��¹���Ϊ" + chInstr + "�Ľ�ʦ��Ϣ��¼��");
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
	    			//ͳһ����ѧ������
	    			boolrs = sta.execute(admininstr_update);
	    			showAdminInstructorList();
	    			//ͬʱ��������
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
	    			alert.setContentText("����ͳһ�޸�ѧ������Ϊ" + stuNum + "�ˣ�");
	    			alert.showAndWait();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	
	//*******��Ķ���*******************************************************************************************
	//�����ʦ��
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
	//�����ʦ��
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