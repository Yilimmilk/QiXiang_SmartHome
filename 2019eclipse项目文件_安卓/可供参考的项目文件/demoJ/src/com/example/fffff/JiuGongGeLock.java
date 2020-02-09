package com.example.fffff;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class JiuGongGeLock extends View {
	private int width = 200;// �ؼ����
	private int height = 300;// �ؼ��߶�
	private float radius;// �뾶
	private float dis;// ���
	private JiuGongPoint points[] = new JiuGongPoint[9];// 9�����λ����Ϣ
	private StringBuffer sPoints = new StringBuffer("");// ѡ�еĵ������λ��
	private String mPass = "012345";// ��ȷ����
	private String mPass1 = "678";
	private float currentX, currentY;// ��¼��ǰ�Ӵ���λ��
	private JiuGongPoint curPoint;// ��¼��ǰѡ�е�
	private boolean checkFlag = false;// ��¼�����ж����
	private boolean moveFlag = false;// �ƶ�״̬
	private int errorCount;// �������
	private OnDrawFinishedListener onDrawFinishedListener;// ����״̬�仯�ӿ��¼�

	// ϵͳҪ��Ĺ��캯��
	public JiuGongGeLock(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public JiuGongGeLock(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		// ����View��С
		setMeasuredDimension(200, 200);
	}

	/**
	 * ��ʼ�� �����ʼ�ŵ��λ��
	 */
	private void init() {
		radius = (Math.min(width, height) / 6.0f) - 10;
		dis = 2 * radius + 5;
		float x0, y0;
		x0 = dis / 2;
		y0 = x0;
		for (int i = 0; i < 9; i++) {
			points[i] = new JiuGongPoint(x0 + (i % 3) * dis, y0 + (i / 3) * dis);
		}
		errorCount = 0;// �����������
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO �Զ����ɵķ������
		Paint paint = new Paint();
		// ���ƾŸ���
		for (JiuGongPoint p : points) {
			canvas.drawCircle(p.x0, p.y0, 8f, paint);
		}
		// ����ѡ�еĵ�����
		if (sPoints.length() > 0) {
			if (checkFlag) {// ���������ȷ�����ɫԲȦ��·��
				paint.setColor(Color.BLUE);
			} else
				paint.setColor(Color.RED);// ����ʹ�ú�ɫ���ʻ���
			paint.setStyle(Paint.Style.STROKE);// ����ԲȦΪ����
			JiuGongPoint prePoint = getPointByIndex(sPoints.charAt(0));// ��ȡ��һ��ѡ�еĵ�
			canvas.drawCircle(prePoint.x0, prePoint.y0, radius, paint);
			for (int i = 1; i < sPoints.length(); i++) {
				JiuGongPoint nowPoint = getPointByIndex(sPoints.charAt(i));
				canvas.drawCircle(nowPoint.x0, nowPoint.y0, radius, paint); // ���Ƶ�ǰ�ڵ�Ŀ���Բ
				canvas.drawLine(prePoint.x0, prePoint.y0, nowPoint.x0,
						nowPoint.y0, paint);// ���Ƶ�ǰ�ڵ㵽��һ��ѡ�е��·��
				prePoint = nowPoint;
			}
			// ����δ�������򻭳���ǰ����λ�õ���һ��ѡ�нڵ��·��
			if (moveFlag) {
				canvas.drawLine(curPoint.x0, curPoint.y0, currentX, currentY,
						paint);
			}
		}
		
	}

	// �����������ַ���ȡ�����
	private JiuGongPoint getPointByIndex(char index) {
		int i = Integer.parseInt("" + index);
		if (i >= 0 && i < 9)
			return points[i];
		return null;
	}

	/**
	 * �������ƴ���
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (checkFlag)
			return false;// ��֤ͨ�������ж�
		currentX = event.getX();
		currentY = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:// ���ƿ�ʼ����ʼ��
			sPoints.delete(0, sPoints.length());// ѡ���б����
			curPoint = null;// ��ǰ��ѡ�нڵ�
			checkFlag = false;// ���¼�⣬�����ʼΪfalse
			moveFlag = true;// �����ƶ�״̬Ϊ�ƶ���ֱ�����뿪��Ļ
			for (JiuGongPoint p : points)
				p.isSelected = false;// ѡ��״̬���
			checkInPoint();// �ж���ǰλ���Ƿ�ѡ�нڵ�
			break;
		case MotionEvent.ACTION_MOVE:// �����ƶ�����ѡ�е����ѡ���б�
			checkInPoint();
			break;
		case MotionEvent.ACTION_UP:// ���ƽ������ж��Ƿ���ȷ
			moveFlag = false;// �����ƶ�
			checkPass();
			break;
		}
		invalidate();// ���½���
		return true;
	}

	// �ж���ǰλ���Ƿ���ڿ�ѡ�нڵ�,�ڷ�Χ��δѡ��������ѡ�����
	private void checkInPoint() {
		for (int i = 0; i < points.length; i++) {
			JiuGongPoint p = points[i];
			if (!p.isSelected) {// ��ǰ�ڵ�δѡ�У����ж������Ƿ�С��ָ���뾶������ѡ�е�ǰ�ڵ�
				if (Math.pow((p.x0 - currentX), 2)
						+ Math.pow((p.y0 - currentY), 2) <= Math.pow(radius, 2)) {
					p.isSelected = true;// �޸Ľڵ�״̬Ϊѡ��
					sPoints.append(i);// �����뵽ѡ���б���
					curPoint = p;// ���µ�ǰѡ�нڵ�
					break;
				}
			}
		}
	}

	/**
	 * �������������Ƿ���ȷ
	 */
	private void checkPass() {
		if (mPass1.equals(sPoints.toString())) {
			checkFlag = true;
			if (null !=  onDrawFinishedListener) {
				onDrawFinishedListener.onSuccess(1);
			}
		} else if (mPass.equals(sPoints.toString())) {
			checkFlag = true;
			if (null != onDrawFinishedListener) {
				onDrawFinishedListener.onSuccess(0);
			}
		} else {
			errorCount++;
			if (null != onDrawFinishedListener) {
				onDrawFinishedListener.onFailure(errorCount);
			}

		}
	}

	public void setOnDrawFinishedListener(
			OnDrawFinishedListener l) {// ���ü����¼�
		this.onDrawFinishedListener = l;
	}
}

// �Ź������״̬�仯�Ǵ����¼�
interface OnDrawFinishedListener {
	// �������ɹ�ʱ�ص��ķ���
	void onSuccess(int count);

	// ������ʧ��ʱ�ص��ķ���
	void onFailure(int errCount);
}

// �Ź�������
class JiuGongPoint {
	public float x0;
	public float y0;
	public boolean isSelected;

	public JiuGongPoint(float x0, float y0) {
		this.x0 = x0;
		this.y0 = y0;
		this.isSelected = false;
	}

}