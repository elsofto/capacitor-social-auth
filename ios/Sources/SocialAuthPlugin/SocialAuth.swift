import Foundation

@objc public class SocialAuth: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
