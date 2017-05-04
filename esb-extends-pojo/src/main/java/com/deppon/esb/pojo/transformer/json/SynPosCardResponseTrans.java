package com.deppon.esb.pojo.transformer.json;

import com.deppon.ecs.inteface.domain.SynPosCardResponse;
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
public class SynPosCardResponseTrans  implements IMessageTransform<SynPosCardResponse> {

        /** The mapper. */
        private static ObjectMapper mapper = new ObjectMapper();

        /**
         * TODO（方法详细描述说明、方法参数的具体涵义）
         * @author 309603
         * @date 2016-7-08
         * @see com.deppon.esb.core.data.IMessageTransform#toMessage(java.lang.String)
         */
        @Override
        public SynPosCardResponse toMessage(String string)
                throws ESBConvertException {
            SynPosCardResponse result = null;
            try {
                result = mapper.readValue(string, SynPosCardResponse.class);
            } catch (JsonParseException e) {
                throw new ESBConvertException("反序列化SmsResult时失败！", e);
            } catch (JsonMappingException e) {
                throw new ESBConvertException("反序列化SmsResult时失败！", e);
            } catch (IOException e) {
                throw new ESBConvertException("反序列化SmsResult时失败！", e);
            }
            return result;
        }

        /**
         * TODO（方法详细描述说明、方法参数的具体涵义）
         * @author 309603
         * @date 2016-7-08
         * @see com.deppon.esb.core.data.IMessageTransform#fromMessage(java.lang.Object)
         */
        @Override
        public String fromMessage(SynPosCardResponse message)
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
