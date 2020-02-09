package com.example.fffff;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
/**
 * ���ն�����ͼ(�����ܹ�400 ����600,��Ҫ�����ߵļ��㣬�ȷ�ʵ�ʹ���200��200/1600 ռ��0.125����400*1.125��õ�50�������
 * ����ͺð��ˣ�500��ȥ��50��õ���������Ҫ���ĵ������450����˼���ǽ�200���յ�������������õ�50��
 * 
 * @author Administrator
 *
 */
public class LineView extends View {
    Paint paint;
   // int num1,num2,num3,num4,num5,num6,num7;
    float[] nums ;

	public LineView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

	}

	public LineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
     public void initData(float[] nums){
    	 
//    	 for (int i = 0; i < nums.length; i++) {
//			nums[i] = nums[i]*2;
//    		 
//		}
    	 
    	 this.nums = nums;
    	 invalidate();
     }
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);


		paint = new Paint();
		canvas.drawLine(100, 100, 100, 500, paint);
		canvas.drawLine(100, 500, 700, 500, paint);
		
		
//		canvas.drawLine(100, 100, 700, 100, paint);
//		canvas.drawLine(100, 200, 700, 200, paint);
//		canvas.drawLine(100, 300, 700, 300, paint);
//		canvas.drawLine(100, 400, 700, 400, paint);
//		
//		canvas.drawLine(200, 100, 200, 500, paint);
//		canvas.drawLine(300, 100, 300, 500, paint);
//		canvas.drawLine(400, 100, 400, 500, paint);
//		canvas.drawLine(500, 100, 500, 500, paint);
//		canvas.drawLine(600, 100, 600, 500, paint);
//		canvas.drawLine(700, 100, 700, 500, paint);
		
		int[] args = {200,400,600,800};
		for(int i=0;i<args.length;i++){
			canvas.drawText(String.valueOf(args[i]), 50, 410-i*100, paint);
		}
		
		//paint.setColor(Color.RED);
		//��һ���㵽���߸���һ�� x����Ϊ100��200.������-700��Y���꼴�����ĸ߶�
		int[] xs = {100,200,300,400,500,600,700};
		
		if(nums!=null){
			for(int i=0;i<xs.length;i++){
				canvas.drawCircle(xs[i], (float) ((int)500-((nums[i]/1600)*400)), 4, paint);
				canvas.drawCircle(xs[i], 500, 4, paint);
				canvas.drawText(nums[i]/2+"", xs[i]-5, 500+15, paint);
				if(i<6){
					canvas.drawLine(xs[i], (float) ((int)500-((nums[i]/1600)*400)), xs[i+1], (float) ((int)500-((nums[i+1]/1600)*400)), paint);
					
				}
				
			}
		}
		
		
	
		
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		//����View��С
		setMeasuredDimension(750,600);
	}
	

}
