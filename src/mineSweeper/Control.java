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
	
	private int init()	//将池子清空
	{
		for(int i=0;i<model.row+2;i++)
			for(int j=0;j<model.col;j++)
				model.setBlock(i, j, 0);
		setMine();
		return 0;
	}
	
	private void setMine()	//在格子里放置雷
	{
		int LastNumOfMine = model.NumOfMine;	
		int i,j;
		Random random=new Random(System.currentTimeMillis());	//以时间为种子生成随机数
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
	
	private void refreshNeighbor()	//刷新存储邻居的矩阵
	{
		int temp=0;
		for(int i=1;i<=model.row;i++)
			for(int j=1;j<=model.col;j++)
			{
				if(model.getBlock(i, j)==3)
				{
					neighbor[i][j]=9;	//如果这一个块是雷，则邻居数记为9
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
	
	//注意：返回值为4时触发结束，要显示爆炸的位置
	//第一次调用本方法必须在ready状态，之后必须在playing状态
	public int open(int i,int j)
	{
		if(FirstOpen)	//第一次打开一定不会踩到雷
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
		case 3:			//踩到雷了
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
	
	private void autoOpen(int i,int j)	//递归实现自动打开相邻的无雷区
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
	
	private boolean isWin()	//判断是否赢了
	{
		for(int i=1;i<=model.row;i++)
			for(int j=1;j<=model.col;j++)	//赢了的条件是只剩下打开的无雷区（代码1）和插对的旗子（代码2）以及未打开的雷（代码3）
				if(model.getBlock(i, j)==0 || model.getBlock(i, j)==5 || model.getBlock(i, j)==4)
					return false;
		model.changeGameState(state.win);
		System.out.println("you win!");
		return true;
	}
}
