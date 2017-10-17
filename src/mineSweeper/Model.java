package mineSweeper;

/*x和y是游戏坐标系，非内部坐标系，取值范围分别为1~row和1~col*/
public class Model
{
	enum state{ready,playing,win,lost};	//游戏状态枚举，有限状态机的四个状态
	private state GameState;
	public int row,col;
	public int NumOfMine;	//雷的数量，与难度相关
	private int[][] block;	//表示雷的二维数组
							/*状态代码：
							0：未打开的无雷块
							1：打开的无雷块
							2：在雷块上插旗
							3：未打开的雷块
							4：打开的雷块
							5：在无雷块上插旗
							*/
	
	public Model(int row, int col, int n)
	{
		block=new int[row+2][col+2];
		this.row=row;
		this.col=col;
		NumOfMine=n;
		for(int i=0;i<row+2;i++)
			for(int j=0;j<col+2;j++)
				block[i][j]=0;
		GameState=state.ready;
	}
	
	public int getBlock(int x,int y)
	{
		if(x<=0||x>=row+1||y<=0||y>=col+1)
			return -1;
		return block[x][y];
	}
	
	public int setBlock(int x,int y,int value)
	{
		if(x<=0||x>=row+1||y<=0||y>=col+1)
			return -1;
		block[x][y]=value;
		return 0;
	}
	
	public int openBlock(int x,int y)
	{
		if(x<=0||x>=row+1||y<=0||y>=col+1)
			return -1;
		block[x][y]=1;
		return 0;
	}
	
	public int setFlag(int x,int y)
	{
		if(x<=0||x>=row+1||y<=0||y>=col+1)
			return -1;
		switch(block[x][y])
		{
		case 0:
			block[x][y]=5;
			break;
		case 2:
			block[x][y]=3;
			break;
		case 3:
			block[x][y]=2;
			break;
		case 5:
			block[x][y]=0;
			break;
		default:
			break;
		}
		return 0;
	}
	
	public void changeGameState(state GameState)
	{
		this.GameState=GameState;
	}
	
	public state getGameState()
	{
		return GameState;
	}
}
