package com.deppon.esb.pojo.transformer.json;

import com.deppon.ecs.inteface.domain.SynPosCardRequest;
import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by yaq on 2016/7/11.
 */
public class SynPosCardRequestTrans implements IMessageTransform<SynPosCardRequest> {


    /** The mapper. */
    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public SynPosCardRequest toMessage(String string)
            throws ESBConvertException {
        SynPosCardRequest result = null;
        try {
            result = mapper.readValue(string, SynPosCardRequest.class);
        } catch (JsonParseException e) {
            throw new ESBConvertException("反序列化SmsResult时失败！", e);
        } catch (JsonMappingException e) {
            throw new ESBConvertException("反序列化SmsResult时失败！", e);
        } catch (IOException e) {
            throw new ESBConvertException("反序列化SmsResult时失败！", e);
        }
        return result;
    }

    @Override
    public String fromMessage(SynPosCardRequest message)
            throws ESBConvertException {
        try {
            return mapper.writeValueAsString(message);
        } catch (JsonGenerationException e) {
            throw new ESBConvertException("序列化SmsResult时失败！", e);
        } catch (JsonMappingException e) {
            throw new ESBConvertException("序列化SmsResult时失败！", e);
        } catch (IOException e) {
            throw new ESBConvertException("序列化SmsResult时失败！", e);
        }
    }
}
