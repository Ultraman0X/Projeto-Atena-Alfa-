package ultraman.athenamod.net;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ClassTokenItem extends Item {
    private final ClassType classType;

    public ClassTokenItem(ClassType classType, Settings settings) {
        super(settings);
        this.classType = classType;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient()) {
            PlayerClassData.set(player, classType.name());
            player.sendMessage(
                    Text.literal("Você agora é um: " + classType.name()),
                    true
            );
            stack.decrement(1);
        }

        return TypedActionResult.success(stack);
    }
}
