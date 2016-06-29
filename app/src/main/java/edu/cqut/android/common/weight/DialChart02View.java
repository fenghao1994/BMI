/**
 * Copyright 2014  XCL-Charts
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @Project XCL-Charts 
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version v0.1
 */

package edu.cqut.android.common.weight;

import java.util.ArrayList;
import java.util.List;

import org.xclcharts.chart.DialChart;
import org.xclcharts.common.MathHelper;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.plot.PlotAttrInfo;
import org.xclcharts.view.GraphicalView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @ClassName DialChart例子
 * @Description  仪表盘例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 *
 */
public class DialChart02View extends GraphicalView {

	private String TAG = "DialChart02View";	
	private DialChart chart = new DialChart();
	private float mPercentage = 0f;
	//真实BMI
	private float truePercentage = 0f;
	
	public DialChart02View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public DialChart02View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public DialChart02View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 
	 private void initView()
	 {
		chartRender();
	 }
	 
	 @Override  
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
	        super.onSizeChanged(w, h, oldw, oldh);  
	        chart.setChartRange(w ,h ); 
	    }  		
						
		public void chartRender()
		{
			try {								
							
				//设置标题背景			
				chart.setApplyBackgroundColor(true);
				chart.setBackgroundColor( Color.rgb(47, 199, 140) );
				//绘制边框
				chart.showRoundBorder();
						
				//设置当前百分比
				//chart.setCurrentPercentage(mPercentage);
				
				//设置指针长度
				chart.getPointer().setLength(0.68f);
				
				//增加轴
				addAxis();						
				/////////////////////////////////////////////////////////////
				//设置附加信息
				addAttrInfo();
				/////////////////////////////////////////////////////////////
				
				chart.getPointer().setPercentage(mPercentage);
				
				chart.getPointer().getPointerPaint().setColor(Color.WHITE);
				chart.getPointer().getBaseCirclePaint().setColor(Color.WHITE);
				//chart.getPointer().setPointerStyle(XEnum.PointerStyle.TRIANGLE);
				
				chart.getPointer().setPercentage(mPercentage/2);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e(TAG, e.toString());
			}
			
		}
		
		public void addAxis()
		{		
			//开始设置轴			
			//轴1 --最外面的弧线轴
			chart.addArcLineAxis(1);			
		
			//轴3 --环形颜色轴
			List<Float> ringPercentage = new ArrayList<Float>();	
			float rper = MathHelper.getInstance().sub(1,mPercentage / 50);
			ringPercentage.add( mPercentage / 50);
			ringPercentage.add( rper);
			
			List<Integer> rcolor  = new ArrayList<Integer>();
			//轴的底色

			if (mPercentage < 18.5f){
				rcolor.add(Color.WHITE);
			}else if(mPercentage <= 23.9f){
				rcolor.add(Color.BLUE);
			}else if(mPercentage <= 28){
				rcolor.add(Color.YELLOW);
			}else{
				rcolor.add(Color.RED);
			}

			//轴的渲染色
			rcolor.add(Color.rgb(99, 214, 173));


			chart.addStrokeRingAxis(0.8f,0.7f, ringPercentage, rcolor);
			
			
			chart.addLineAxis(XEnum.Location.TOP,0.3f); 	
			chart.addLineAxis(XEnum.Location.LEFT,0.3f); 	
			chart.addLineAxis(XEnum.Location.RIGHT,0.3f); 				
			if(chart.getPlotAxis().size() >= 2)
			{
				chart.getPlotAxis().get(2).getAxisPaint().setColor(Color.BLUE);	
				chart.getPlotAxis().get(2).getAxisPaint().setStrokeWidth(5);
			}
			if(chart.getPlotAxis().size() >= 3)
			{
				chart.getPlotAxis().get(3).getAxisPaint().setColor(Color.GREEN);	
				chart.getPlotAxis().get(3).getAxisPaint().setStrokeWidth(5);
			}
			if(chart.getPlotAxis().size() >= 4)
			{
				chart.getPlotAxis().get(4).getAxisPaint().setColor(Color.YELLOW);	
				chart.getPlotAxis().get(4).getAxisPaint().setStrokeWidth(5);
			}

			
			chart.getPlotAxis().get(0).getAxisPaint().setColor(Color.WHITE );
			chart.getPlotAxis().get(0).getAxisPaint().setStrokeWidth(2);
			chart.getPlotAxis().get(1).getFillAxisPaint().setColor(Color.rgb(47, 199, 140) );
		
			chart.addCircleAxis(0.2f,Color.rgb(62, 175, 135)); 
			chart.addCircleAxis(0.15f,Color.rgb(28, 111, 84)); 
		}
		
		
		private void addAttrInfo()
		{
			/////////////////////////////////////////////////////////////
			PlotAttrInfo plotAttrInfo = chart.getPlotAttrInfo();
		
			//设置附加信息
			Paint paintTB = new Paint();
			paintTB.setTextAlign(Align.CENTER);
			paintTB.setTextSize(40);

			Paint paintBT = new Paint();

			//根据BMI指数的值的不同显示不同文字和颜色
			if (mPercentage < 18.5f){
				paintTB.setColor(Color.WHITE);
				paintBT.setColor(Color.WHITE);
				plotAttrInfo.addAttributeInfo( XEnum.Location.TOP, "偏瘦", 0.9f, paintTB);
			}else if(mPercentage <= 23.9f){
				paintTB.setColor(Color.BLUE);
				paintBT.setColor(Color.BLUE);
				plotAttrInfo.addAttributeInfo( XEnum.Location.TOP, "标准", 0.9f, paintTB);
			}else if(mPercentage <= 28){
				paintTB.setColor(Color.YELLOW);
				paintBT.setColor(Color.YELLOW);
				plotAttrInfo.addAttributeInfo( XEnum.Location.TOP, "偏胖", 0.9f, paintTB);
			}else{
				paintTB.setColor(Color.RED);
				paintBT.setColor(Color.RED);
				plotAttrInfo.addAttributeInfo( XEnum.Location.TOP, "肥胖", 0.9f, paintTB);
			}

			paintBT.setTextAlign(Align.CENTER);
			paintBT.setTextSize(50);
			
			
			plotAttrInfo.addAttributeInfo(XEnum.Location.BOTTOM, 
					"BMI: "+Float.toString( truePercentage), 0.8f, paintBT);
		}
		
		public void setCurrentStatus(float percentage)
		{
			//清理
			chart.clearAll();
			truePercentage = percentage;
			if (percentage >= 50){
				mPercentage = 50;
			}else {
				mPercentage =  percentage;
			}
			//设置当前百分比
			chart.getPointer().setPercentage(mPercentage / 50);
			addAxis();
			addAttrInfo();
		}


		@Override
		public void render(Canvas canvas) {
			// TODO Auto-generated method stub
			 try{
		            chart.render(canvas);
		            
		        } catch (Exception e){
		        	Log.e(TAG, e.toString());
		        }
		}

}
