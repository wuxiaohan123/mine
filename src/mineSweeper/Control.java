package mineSweeper;
import java.util.Random;
import mineSweeper.Model.state;

public class Control
{
	public Model model;
	private int[][] neighbor;
	private boolean FirstOpen=true;
	
	public Control(int row,int col,int n)
	{
		model=new Model(row,col,n);
		neighbor = new int[row+2][col+2];
		init();
	}
	
	private int init()	//���������
	{
		for(int i=0;i<model.row+2;i++)
			for(int j=0;j<model.col;j++)
				model.setBlock(i, j, 0);
		setMine();
		return 0;
	}
	
	private void setMine()	//�ڸ����������
	{
		int LastNumOfMine = model.NumOfMine;	
		int i,j;
		Random random=new Random(System.currentTimeMillis());	//��ʱ��Ϊ�������������
		while(LastNumOfMine>0)
		{
			i=Math.abs(random.nextInt())%model.row+1;
			j=Math.abs(random.nextInt())%model.col+1;
			if(model.getBlock(i, j)==0)
			{
				model.setBlock(i, j, 3);
				LastNumOfMine--;
			}
		}
		refreshNeighbor();
		model.changeGameState(Model.state.ready);	
	}
	
	private void refreshNeighbor()	//ˢ�´洢�ھӵľ���
	{
		int temp=0;
		for(int i=1;i<=model.row;i++)
			for(int j=1;j<=model.col;j++)
			{
				if(model.getBlock(i, j)==3)
				{
					neighbor[i][j]=9;	//�����һ�������ף����ھ�����Ϊ9
					continue;
				}
				temp=0;
				if(model.getBlock(i-1, j-1)==3)
					temp++;
				if(model.getBlock(i-1, j)==3)
					temp++;
				if(model.getBlock(i-1, j+1)==3)
					temp++;
				if(model.getBlock(i, j-1)==3)
					temp++;
				if(model.getBlock(i, j+1)==3)
					temp++;
				if(model.getBlock(i+1, j-1)==3)
					temp++;
				if(model.getBlock(i+1, j)==3)
					temp++;
				if(model.getBlock(i+1, j+1)==3)
					temp++;
				neighbor[i][j]=temp;
			}
	}
	
	public int getNeighbor(int i,int j)
	{
		return neighbor[i][j];
	}
	
	//ע�⣺����ֵΪ4ʱ����������Ҫ��ʾ��ը��λ��
	//��һ�ε��ñ�����������ready״̬��֮�������playing״̬
	public int open(int i,int j)
	{
		if(FirstOpen)	//��һ�δ�һ������ȵ���
		{
			while(model.getBlock(i, j)==3)
				init();
			model.changeGameState(state.playing);
			autoOpen(i, j);
			isWin();
			FirstOpen=false;
		}
		if(model.getGameState()!=state.playing)
			return -1;
		switch(model.getBlock(i, j))
		{
		case 0:
			autoOpen(i, j);
			isWin();
			return 1;
		case 3:			//�ȵ�����
			model.setBlock(i, j, 4);
			model.changeGameState(state.lost);
			System.out.println("game over!");
			return 4;
		case 1:
		case 2:
		default:
			return 0;
		}
	}
	
	private void autoOpen(int i,int j)	//�ݹ�ʵ���Զ������ڵ�������
	{
		if(i<=0||i>=model.row+1)
			return;
		else if(j<=0||j>=model.col+1)
			return;
		model.setBlock(i, j, 1);
		if(neighbor[i][j]==0 && model.getBlock(i-1, j-1)==0)
			autoOpen(i-1, j-1);
		if(neighbor[i][j]==0 && model.getBlock(i-1, j)==0)
			autoOpen(i-1, j);
		if(neighbor[i][j]==0 && model.getBlock(i-1, j+1)==0)
			autoOpen(i-1, j+1);
		if(neighbor[i][j]==0 && model.getBlock(i, j-1)==0)
			autoOpen(i, j-1);
		if(neighbor[i][j]==0 && model.getBlock(i, j+1)==0)
			autoOpen(i, j+1);
		if(neighbor[i][j]==0 && model.getBlock(i+1, j-1)==0)
			autoOpen(i+1, j-1);
		if(neighbor[i][j]==0 && model.getBlock(i+1, j)==0)
			autoOpen(i+1, j);
		if(neighbor[i][j]==0 && model.getBlock(i+1, j+1)==0)
			autoOpen(i+1, j+1);
		System.out.println("open:"+j+","+i);
	}
	
	private boolean isWin()	//�ж��Ƿ�Ӯ��
	{
		for(int i=1;i<=model.row;i++)
			for(int j=1;j<=model.col;j++)	//Ӯ�˵�������ֻʣ�´򿪵�������������1���Ͳ�Ե����ӣ�����2���Լ�δ�򿪵��ף�����3��
				if(model.getBlock(i, j)==0 || model.getBlock(i, j)==5 || model.getBlock(i, j)==4)
					return false;
		model.changeGameState(state.win);
		System.out.println("you win!");
		return true;
	}
}
