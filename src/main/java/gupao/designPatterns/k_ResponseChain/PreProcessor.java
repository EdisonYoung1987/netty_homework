package gupao.designPatterns.k_ResponseChain;

/**预处理器*/
public class PreProcessor implements IProcesser{
    private ProcessorChain chain;
    public PreProcessor(ProcessorChain chain){
        this.chain=chain;
        chain.registProcessor(this);
    }

    @Override
    public void doProcess(Request request, Response response) {
        String msg=request.getMessage();
        request.setMessage("预处理后:"+msg);
        chain.getNextProcessor(this).doProcess(request,response);
    }


}
