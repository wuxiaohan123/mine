package mineSweeper;

import mineSweeper.Model.state;

public class ViewForConsole	//����̨��ͼ��
{
	public void debugShow(Control control)	//��ʾ���״̬����
	{
		for(int i=1;i<=control.model.row;i++)
		{	
			for(int j=1;j<=control.model.col;j++)
				System.out.print(control.model.getBlock(i, j));
			System.out.println();
		}
		System.out.println();
	}
	
	public void showNeighbor(Control control)	//��ʾ�ھӾ���
	{
		for(int i=1;i<=control.model.row;i++)
		{	
			for(int j=1;j<=control.model.col;j++)
				System.out.print(control.getNeighbor(i, j));
			System.out.println();
		}
	}
	
	public void show(Control control)	//��Ϸ��ͼ
	{
		for(int i=1;i<=control.model.row;i++)
		{	
			for(int j=1;j<=control.model.col;j++)
				switch(control.model.getBlock(i, j))
				{
				case 0:
				case 3:
				default:
					System.out.print('#');
					break;
				case 1:
					if(control.getNeighbor(i, j)==0)
						System.out.print(' ');
					else
						System.out.print(control.getNeighbor(i, j));
					break;
				case 2:
					System.out.print('P');
					break;
				case 4:
					System.out.print('X');
					break;	
				case 5:
					if(control.model.getGameState()==state.playing)
						System.out.print('P');
					else if(control.model.getGameState()==state.lost)
						System.out.print('X');
					break;
				}
			System.out.println();
		}
		if(control.model.getGameState()==state.lost)
			System.out.println("you are lost.");
	}
}
