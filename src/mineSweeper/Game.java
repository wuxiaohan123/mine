package mineSweeper;
import java.util.Scanner;
import mineSweeper.Model.state;

public class Game
{
	public Control control;
	
	ViewForConsole view=new ViewForConsole();
	Scanner scanner = new Scanner(System.in);	//��ȡ��������֣�ÿ�ζ�ȡһ��������
	int row,col;
	
	public Game(int row,int col,int n)
	{
		control = new Control(row,col,n);
	}
	
	public void go()
	{
		while(true)
		{
			view.show(control);
			//view.debugShow(control);
			//view.showNeighbor(control);
			row=scanner.nextInt();
			col=scanner.nextInt();
			if(control.model.getGameState()!=state.win && control.model.getGameState()!=state.lost)
				control.open(row, col);
		}
	}
}
