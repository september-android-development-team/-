package com.test.september.util.effects.control;


import com.test.september.util.effects.BaseEffects;
import com.test.september.util.effects.FadeIn;
import com.test.september.util.effects.Fall;
import com.test.september.util.effects.FlipH;
import com.test.september.util.effects.FlipV;
import com.test.september.util.effects.NewsPaper;
import com.test.september.util.effects.RotateBottom;
import com.test.september.util.effects.RotateLeft;
import com.test.september.util.effects.Shake;
import com.test.september.util.effects.SideFall;
import com.test.september.util.effects.SlideBottom;
import com.test.september.util.effects.SlideLeft;
import com.test.september.util.effects.SlideRight;
import com.test.september.util.effects.SlideTop;
import com.test.september.util.effects.Slit;

/*
 * Copyright 2014 litao
 * https://github.com/sd6352051/NiftyDialogEffects
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
 */
public enum Effectstype {

    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    SlideBottom(com.test.september.util.effects.SlideBottom.class),
    Slideright(SlideRight.class),
    Fall(com.test.september.util.effects.Fall.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    RotateBottom(com.test.september.util.effects.RotateBottom.class),
    RotateLeft(com.test.september.util.effects.RotateLeft.class),
    Slit(com.test.september.util.effects.Slit.class),
    Shake(com.test.september.util.effects.Shake.class),
    Sidefill(SideFall.class);
    private Class<? extends BaseEffects> effectsClazz;

    private Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        BaseEffects bEffects = null;
        try {
            bEffects = effectsClazz.newInstance();
        } catch (ClassCastException e) {
            throw new Error("Can not init animatorClazz instance");
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            throw new Error("Can not init animatorClazz instance");
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            throw new Error("Can not init animatorClazz instance");
        }
        return bEffects;
    }
/**
* 调用方法
* **/

//            Fadein         //中心凸显
//            Slideright     //右侧渐现
//            Slileft        //左侧渐现
//            Sliddeetop     //顶部渐现
//            SlideBottom    //底部渐现
//            Newspager      //旋转报纸
//            Fall           //向下坠落
//            Sidefill
//            Fliph
//            Flipv
//            RotateBottom  //底部翻转
//            RotateLeft    //
//            Slit          //左侧翻转
//            Shake
//        }
}
