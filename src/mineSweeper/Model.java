package mineSweeper;

/*x��y����Ϸ����ϵ�����ڲ�����ϵ��ȡֵ��Χ�ֱ�Ϊ1~row��1~col*/
public class Model
{
	enum state{ready,playing,win,lost};	//��Ϸ״̬ö�٣�����״̬�����ĸ�״̬
	private state GameState;
	public int row,col;
	public int NumOfMine;	//�׵����������Ѷ����
	private int[][] block;	//��ʾ�׵Ķ�ά����
							/*״̬���룺
							0��δ�򿪵����׿�
							1���򿪵����׿�
							2�����׿��ϲ���
							3��δ�򿪵��׿�
							4���򿪵��׿�
							5�������׿��ϲ���
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
