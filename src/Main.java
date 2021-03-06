import java.util.Map;
import java.util.concurrent.TimeUnit;
import conversationScope.ConversationManager;
import conversationScope.Conversation;
import conversationScope.ConversationException;

public class Main {
    public static void main(String[] args)throws ConversationException{
        //conversationScope.ConversationManager.getInstance().getConversation(1).setValue("name", new StringBuffer("aaaa"));
        //conversationScope.ConversationManager.getInstance().getConversation(2).setValue("name", "bbbb");

        //StringBuffer nameA=(StringBuffer)conversationScope.ConversationManager.getInstance().getConversation(1).getReference("name");
        //String nameB=(String)conversationScope.ConversationManager.getInstance().getConversation(2).getReference("name");
        //conversationScope.ConversationManager.getInstance().getConversation(1).begin();
        //conversationScope.ConversationManager.getInstance().getConversation(1).endRequest();
        //nameA.append(" cccc");
        String id = testSet("1");
        Conversation conv = ConversationManager.getInstance().getConversation(id);
        System.out.println(conv.getValue("name1"));
        System.out.println(conv.getValue("name2"));

        //podmiana obiektu o już istniejącej nazwie
        conv.setValue("name1",new StringBuffer("cccc"));
        System.out.println(conv.getValue("name1"));
        System.out.println(conv.getValue("name2"));
        //conv.end(); // zmieniamy stan konwersacji z long running na short running
        Conversation nestedConv = conv.AddNestedConversation(20*1000).begin();
        conv.endRequest();
        long tt = System.currentTimeMillis();
        try{
            TimeUnit.SECONDS.sleep(4);
        }catch(Exception e){}


        nestedConv.endRequest();

        Map<String, Conversation> convs = ConversationManager.getInstance().getConversationsMap();
        System.out.println("number of convs: " + Integer.toString(convs.size()));

        try{
            TimeUnit.SECONDS.sleep(10);
        }catch(Exception e){

        }

        convs = ConversationManager.getInstance().getConversationsMap();
        System.out.println("number of convs: " + Integer.toString(convs.size()));

//        String time = Long.toString(tt-System.currentTimeMillis());
//        System.out.println(time);
//        conversationScope.Conversation conv1 = conversationScope.ConversationManager.getInstance().getConversation(id);
//        System.out.println(conv1.getReference("name1"));
//        System.out.println(conv1.getReference("name2"));
        //StringBuffer nameC=(StringBuffer)conversationScope.ConversationManager.getInstance().getConversation(1).getReference("name");
        //System.out.println(nameC);

    }
    public static String testSet(String id)throws ConversationException{
        Conversation conv = ConversationManager.getInstance().createConversation(5*1000);
        Conversation conv1 = ConversationManager.getInstance().createConversation(7*1000);
        conv.setValue("name1", new StringBuffer("aaaa"));
        conv.setValue("name2", new StringBuffer("bbbb"));
        conv.begin();
        //conv1.begin();
        String ret = conv.getId();
        conv1.begin().endRequest();
        conv.endRequest();
        return ret;
    }
}
