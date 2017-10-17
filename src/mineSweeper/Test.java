package mineSweeper;

public class Test
{
	Model model1=new Model(10, 10, 8);
	public void go()
	{
		for(int i=0;i<10;i++)
		{
			model1.setBlock(i,i,2);
		}
		
		model1.setBlock(2, 1, 3);
		
		model1.openBlock(6, 8);
		
		model1.setFlag(4, 4);
		
		
		for(int i=1;i<=10;i++)
		{
			for(int j=1;j<=10;j++)
				System.out.print(model1.getBlock(i, j));
			System.out.println();
		}		
	}
}
