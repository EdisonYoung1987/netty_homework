package gupao.designPatterns.k_ResponseChain;

/**逻辑处理器*/
public class DoProcessor implements IProcesser{
    private ProcessorChain chain;
    public DoProcessor(ProcessorChain chain){
        this.chain=chain;
        chain.registProcessor(this);
    }

    @Override
    public void doProcess(Request request, Response response) {
        String msg=request.getMessage();
        request.setMessage("处理中:"+msg);
        chain.getNextProcessor(this).doProcess(request,response);
    }
}
