package Items;

import java.util.Scanner;
import Characters.MainEntity;
import Strategies.AoeTargetPS;
import Strategies.SingleTargetPS;
import Strategies.UniSkill;

import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

public class PowerStone extends Item{
    Scanner newscan = new Scanner(System.in);
    
    
    public PowerStone(){
        super("PowerStone");
    }

    public String getName(){return this.name;}

    @Override
    public void useItem(MainEntity player, List<MainEntity> enemies){
        System.out.println("PowerStone used - Free use of skill");
        
        UniSkill aoeSkillPS = new AoeTargetPS(0);
        UniSkill singleSkillPS = new SingleTargetPS(0);

        UniSkill skill = player.isAoE() ? aoeSkillPS:singleSkillPS;

        List<MainEntity> targets = selectTargets(player, enemies);
        skill.execute(player, targets);
    }

    private MainEntity selectTarget(List<MainEntity> enemies){
        List<MainEntity> living = enemies.stream().filter(e->e.getHealth()>0).collect(Collectors.toList());
        

        System.out.print("\nChoose who to attack: \n");
        for (int i = 0; i<living.size(); i++){
            System.out.print("[" + (i+1) + "] " + living.get(i).getName() + " ("+living.get(i).getHealth()+")  ");
        }
        System.out.println("\n");
        
        while (true){
            try{
                int target = newscan.nextInt();
                if (target < 1 || target > living.size()){System.out.println("Out of range, try again."); continue;}

                return living.get(target-1);

            }catch(InputMismatchException e){
                System.out.println("Invalid input, try again.");
                newscan.nextLine();
            }
        }
    }

    private List<MainEntity> selectTargets(MainEntity player, List<MainEntity> enemies){
        if (player.isAoE()){
            return enemies.stream().filter(e->e.getHealth()>0).collect(Collectors.toList());
        }else{
            return List.of(selectTarget(enemies));
        }
    }
}
