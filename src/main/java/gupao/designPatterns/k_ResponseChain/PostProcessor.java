package gupao.designPatterns.k_ResponseChain;

/**后处理器*/
public class PostProcessor implements IProcesser{
    private ProcessorChain chain;
    public PostProcessor(ProcessorChain chain){
        this.chain=chain;
        chain.registProcessor(this);
    }

    @Override
    public void doProcess(Request request, Response response) {
        String msg=request.getMessage();
        request.setMessage("后处理:"+msg);
        IProcesser processer= chain.getNextProcessor(this);
        if(processer!=null)
            doProcess(request,response);
    }
}
