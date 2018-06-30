import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.util.Arrays;

public class Test {
    public static void main(String[] rgs){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("测试1");
        out.writeUTF("测试2");
        ByteArrayDataInput in = ByteStreams.newDataInput(out.toByteArray());
        String[] arr = new String[]{"1","2","3"};
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr,1,arr.length)));
    }
}
