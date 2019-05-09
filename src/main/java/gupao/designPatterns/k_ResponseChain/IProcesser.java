package gupao.designPatterns.k_ResponseChain;

public interface IProcesser  {
//    public IProcesser(ProcessorChain);
    void doProcess(Request request,Response response);
}
