package mineSweeper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application
{
	//以下为标准扫雷的难度设置
	//row*col*n
	//大：16*30*99
	//中：16*16*40
	//小：10*10*10
	static int row = 16;
	static int col = 30;
	static int n = 99;
	double ButtonSize = 30.0;
	Control control = new Control(col, row, n);
	Button[][] button = new Button[col][row];
	
	@Override
	public void start(Stage primaryStage)
	{
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		primaryStage.getIcons().add(new Image("/mineSweeper/MineSweeper.jpg"));
		
		for(int i = 0; i < col; i++)
			for(int j = 0; j < row; j++)
			{
				button[i][j] = new Button("  ");
				ButtonHandlerClass handler = new ButtonHandlerClass();
				handler.getControl(control, button, i, j);
				button[i][j].setOnAction(handler);
				button[i][j].setMinSize(ButtonSize, ButtonSize);
				button[i][j].setPrefSize(ButtonSize, ButtonSize);
				button[i][j].setMaxSize(ButtonSize, ButtonSize);
				pane.add(button[i][j], i, j);
			}
		
		Scene scene = new Scene(pane,col*ButtonSize,row*ButtonSize);
		primaryStage.setTitle("MineSweeper");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();	
	}
	
	public static void main(String[] args)
	{
		/*命令行使用以下两句*/
		//Game game1=new Game(row,col,n);
		//game1.go();
		
		/*JavaFX使用这一句，并屏蔽上一句*/
		Application.launch(args);
	}
}

class ButtonHandlerClass implements EventHandler<ActionEvent>
{
	private Control control;
	private Button[][] button;
	private int i,j;
	
	@Override
	public void handle(ActionEvent e)
	{
		control.open(i+1, j+1);						//点哪个先打开哪个
		for(int x = 0; x < control.model.row; x++)
			for(int y = 0; y < control.model.col; y++)
			{
				if(control.model.getBlock(x+1, y+1)==1)		//对打开的块显示
				{
					button[x][y].setStyle("-fx-base:#777777;");
					if(control.getNeighbor(x+1, y+1)!=0)	//邻居为0的块不显示数字
						button[x][y].setText(String.valueOf(control.getNeighbor(x+1, y+1)));	//显示邻居数
				}
				else if(control.model.getBlock(x+1, y+1)==4)	//爆炸瞬间，参看model类
					button[x][y].setStyle("-fx-base:#ee2211;");
			}
	}
	
	public void getControl(Control control, Button[][] button, int i, int j)	//传输数据，包括控制类和按钮对象数组
	{
		this.control = control;
		this.button = button;
		this.i = i;
		this.j = j;
	}
}