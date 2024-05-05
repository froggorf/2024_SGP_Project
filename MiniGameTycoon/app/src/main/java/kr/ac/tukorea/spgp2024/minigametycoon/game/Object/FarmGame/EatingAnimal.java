package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FarmGame;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.AnimSprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FarmGameScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;
import kr.ac.tukorea.spgp2024.minigametycoon.game.enums.EFarmAnimalType;

public class EatingAnimal implements IGameObject {
    float MaxLifeSpan = 1.0f;
    float CurrentLifeSpan = 0.0f;
    float AnimalSize;
    boolean bSuccessEating;
    EFarmAnimalType AnimalType;
    Sprite EatingAnimalSprite;
    RectF AnimalRectF;
    AnimSprite SuccessEffect;
    public EatingAnimal(EFarmAnimalType Type,boolean SuccessEating){
        AnimalSize = UserDisplay.getWidth(0.2f);
        bSuccessEating = SuccessEating;

        AnimalType = Type;

        // 동물 위치 구하기
        AnimalRectF = FarmGameScene.GetBowlRect(Type.ordinal());
        AnimalRectF.top -= UserDisplay.getHeight(0.1f);
        AnimalRectF.right = AnimalRectF.bottom = UserDisplay.getWidth(0.4f);

        // 동물 생성
        EatingAnimalSprite = new Sprite(FarmGameScene.AnimalResourceArray[Type.ordinal()],
                AnimalRectF.left, AnimalRectF.top,AnimalRectF.right,AnimalRectF.bottom
        );

        // 정답/오답 이펙트 생성
        int EffectResId = 0;
        if(bSuccessEating)
        {
            EffectResId = R.mipmap.temp_farmgame_eatinganimal_success;
        }
        else{
            EffectResId = R.mipmap.temp_farmgame_eatinganimal_wrong;
        }
        SuccessEffect = new AnimSprite(EffectResId, AnimalRectF.left, AnimalRectF.top,AnimalRectF.right,AnimalRectF.bottom, MaxLifeSpan*4,4);



    }
    @Override
    public void update() {
        CurrentLifeSpan += BaseScene.frameTime;
        if(CurrentLifeSpan >= MaxLifeSpan){
            BaseScene.getTopScene().remove(FarmGameScene.Layer.EATING_ANIMAL, this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        EatingAnimalSprite.draw(canvas);
        SuccessEffect.draw(canvas);
    }
}
