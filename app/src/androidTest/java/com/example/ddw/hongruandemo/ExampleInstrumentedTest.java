package com.example.ddw.hongruandemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.zjxfyb.whf.facecomparedemo.api.FaceSetImpl;
import com.zjxfyb.whf.facecomparedemo.callBack.FaceCallBack;
import com.zjxfyb.whf.facecomparedemo.imageloader.ImageLoader;
import com.zjxfyb.whf.facecomparedemo.modle.GetFaceSetsBean;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String TAG = ExampleInstrumentedTest.class.getSimpleName();

    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
//        DBO_Liaohe_util util = new DBO_Liaohe_util(appContext);
//        LiaoHeBean bean = new LiaoHeBean("红茶水箱", 8, 1000);
//        bean.setTroughId(4);
//        bean.setFormula_id("3333333");
//        bean.setEarlyWarningCapacity(100);
//        bean.setUsed(true);
//        boolean isTrue = util.insertData(bean);
//        boolean isTrue = util.updateData(bean.getFormula_id(), bean);
//        Log.e(TAG, "useAppContext: " + isTrue);
//        util.searchAll();
//        util.searchByName("纯净水箱");
//        util.deteleAll();


//        deleateFaceSets(appContext);

        ImageLoader.load("http://pay.bsrobots.com/ddw-manager/images/57xxxhdpi.png").downloadImage();
    }


    /**
     * 删除人脸库操作
     */
    private void deleateFaceSets(final Context context) {

        FaceSetImpl.getFaceSets(context, null, new FaceCallBack<GetFaceSetsBean>() {

            @Override
            public void onSuccess(GetFaceSetsBean body) {

                Log.e(TAG, "onSuccess: 查找到faceset集合 ： " + body);

                List<GetFaceSetsBean.FacesetsBean> facesets = body.getFacesets();

                for (GetFaceSetsBean.FacesetsBean bean : facesets) {

                    FaceSetImpl.deleteFaceForFaceSetToken(context, bean.getFaceset_token(), 0, new FaceCallBack<String>() {
                        @Override
                        public void onSuccess(String body) {
                            Log.e(TAG, "onSuccess: 删除人脸库成功 " + body);
                        }

                        @Override
                        public void onFaild(String body) {
                            Log.e(TAG, "onSuccess: 删除人脸库失败 " + body);
                        }
                    });
                }

            }

            @Override
            public void onFaild(String body) {

                Log.e(TAG, "onFaild: 网络异常，没有获取到人脸集合 " + body);


            }
        });
    }
}
