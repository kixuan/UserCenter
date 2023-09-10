import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import {SYSTEN_LOGO} from "@/constants";

const Footer: React.FC = () => {
  const defaultMessage = '炫仔技术部出品';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'Blog',
          title: '炫仔博客111',
          // 注意这里的SYSTEN_LOGO不要用单引号引起来，不然被解析成字符串了
          href: SYSTEN_LOGO,
          blankTarget: true,
        },
        {
          key: '炫仔github',
          title: <GithubOutlined />,
          href: 'https://github.com/kixuan',
          blankTarget: true,
        },
        {
          key: 'Bilibili',
          title: '七个b站都不够我刷',
          href: 'https://space.bilibili.com/401580681?spm_id_from=333.1007.0.0',
          blankTarget: true,
        },
      ]}
    />
  );
};
export default Footer;
